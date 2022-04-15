package ru.geekbrains.march.market.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.cart.integrations.ProductServiceIntegration;
import ru.geekbrains.march.market.cart.utils.Cart;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productService;
    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
        cart.setItems(new ArrayList<>());
    }

    public Cart getCurrentCart() {
        return cart;
    }

    public void addToCart(Long productId) {
        ProductDto p = productService.findById(productId);
        cart.add(p);
    }

    public void clearCart() {
        cart.clear();
    }

    public void deleteProductFromCart(Long productId) {
        ProductDto p = productService.findById(productId);
        cart.delete(p);
    }

    public void removeItemFromCart(Long productId) {
        ProductDto p = productService.findById(productId);
        cart.remove(p);
    }
}
