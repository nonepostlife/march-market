package ru.geekbrains.march.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.march.market.entities.Product;
import ru.geekbrains.march.market.services.CartService;
import ru.geekbrains.march.market.services.ProductService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final ProductService productService;
    private final CartService cartService;

    @GetMapping
    public List<Product> getCart(HttpSession session) {
        return cartService.findAll(session);
    }

    @PostMapping("/add/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addProductToCart(@PathVariable Long id, HttpSession session) {
        Product product = productService.findById(id);
        cartService.saveProductToCart(session, product);
    }
}
