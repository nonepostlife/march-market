package ru.geekbrains.march.market.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.cart.execptions.ResourceNotFoundException;
import ru.geekbrains.march.market.cart.integrations.ProductServiceIntegration;
import ru.geekbrains.march.market.cart.utils.Cart;
import ru.geekbrains.march.market.cart.utils.CartItem;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    public Cart getCurrentCart(String cartId) {
        if (!redisTemplate.hasKey(cartId)) {
            Cart cart = new Cart();
            redisTemplate.opsForValue().set(cartId, cart);
        }
        return (Cart) redisTemplate.opsForValue().get(cartId);
    }

    public void addToCart(String cartId, Long productId) {
        addToCart(cartId, productId, 1);
    }

    public void addToCart(String cartId, Long productId, int quantity) {
        execute(cartId, cart -> {
            try {
                ProductDto p = productServiceIntegration.findById(productId);
                for (int i = 0; i < quantity; i++) {
                    cart.add(p);
                }
            } catch (WebClientResponseException e) {
                throw new ResourceNotFoundException(String.format("Продукт с id '%s' не найден", productId));
            }
        });
    }

    public void clearCart(String cartId) {
        execute(cartId, Cart::clear);
    }

    public void deleteProductFromCart(String cartId, Long productId) {
        execute(cartId, cart -> {
            try {
                ProductDto p = productServiceIntegration.findById(productId);
                cart.deleteProduct(p);
            } catch (WebClientResponseException e) {
                throw new ResourceNotFoundException(String.format("Продукт с id '%s' не найден", productId));
            }
        });
    }

    public void removeItemFromCart(String cartId, Long productId) {
        execute(cartId, cart -> {
            try {
                ProductDto p = productServiceIntegration.findById(productId);
                cart.removeItemProduct(p);
            } catch (WebClientResponseException e) {
                throw new ResourceNotFoundException(String.format("Продукт с id '%s' не найден", productId));
            }
        });
    }

    private void execute(String cartId, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartId);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartId, cart);
    }

    public void mergeCart(String username, String guestCartId) {
        Cart guestCart = getCurrentCart(guestCartId);
        if (!guestCart.getItems().isEmpty()) {
            for (CartItem item : guestCart.getItems()) {
                addToCart(username, item.getProductId(), item.getQuantity());
            }
        }
    }
}
