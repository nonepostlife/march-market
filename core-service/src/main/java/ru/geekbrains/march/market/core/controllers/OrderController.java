package ru.geekbrains.march.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.march.market.api.OrderDto;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.core.converters.ProductConverter;
import ru.geekbrains.march.market.core.exceptions.ResourceNotFoundException;
import ru.geekbrains.march.market.core.services.OrderService;
import ru.geekbrains.march.market.core.services.ProductService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("checkout")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewOrder(Principal principal) {
        orderService.createNewOrder(principal.getName());
    }

    @GetMapping
    public List<OrderDto> getAllOrders(Principal principal) {
        return orderService.getAllOrders(principal.getName());
    }

}
