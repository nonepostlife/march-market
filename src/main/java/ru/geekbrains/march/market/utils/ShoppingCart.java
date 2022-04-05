package ru.geekbrains.march.market.utils;

import lombok.Data;
import ru.geekbrains.march.market.entities.Product;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingCart {
    private List<Product> cart;

    public ShoppingCart() {
        this.cart = new ArrayList<>();
    }

    public void addProduct(Product p) {
        cart.add(p);
    }
}
