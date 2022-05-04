package ru.geekbrains.march.market.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.cart.integrations.ProductServiceIntegration;
import ru.geekbrains.march.market.cart.utils.Cart;
import ru.geekbrains.march.market.cart.utils.CartItem;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private Map<String, Cart> carts;

    @PostConstruct
    public void init() {
        carts = new HashMap<>();
    }

    public Cart getCurrentCart(String cartId) {
        if (!carts.containsKey(cartId)) {
            Cart cart = new Cart();
            carts.put(cartId, cart);
        }
        return carts.get(cartId);
    }

    public void addToCart(String cartId, Long productId) {
        addToCart(cartId, productId, 1);
    }

    public void addToCart(String cartId, Long productId, int quantity) {
        ProductDto p = productServiceIntegration.findById(productId);
        for (int i = 0; i < quantity; i++) {
            getCurrentCart(cartId).add(p);
        }
    }

    public void clearCart(String cartId) {
        getCurrentCart(cartId).clear();
    }

    public void deleteProductFromCart(String cartId, Long productId) {
        ProductDto p = productServiceIntegration.findById(productId);
        getCurrentCart(cartId).delete(p);
    }

    public void removeItemFromCart(String cartId, Long productId) {
        ProductDto p = productServiceIntegration.findById(productId);
        getCurrentCart(cartId).remove(p);
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
