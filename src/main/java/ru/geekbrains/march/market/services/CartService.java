package ru.geekbrains.march.market.services;

import org.springframework.stereotype.Service;
import ru.geekbrains.march.market.entities.Product;
import ru.geekbrains.march.market.utils.ShoppingCart;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class CartService {

    public List<Product> findAll(HttpSession session) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new ShoppingCart();
        }
        return cart.getCart();
    }

    public void saveProductToCart(HttpSession session, Product product) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new ShoppingCart();
        }
        cart.addProduct(product);
        session.setAttribute("cart", cart);
    }
}
