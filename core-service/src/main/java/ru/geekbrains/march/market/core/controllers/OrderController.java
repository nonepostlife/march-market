package ru.geekbrains.march.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.march.market.api.ContactInfo;
import ru.geekbrains.march.market.api.OrderDto;
import ru.geekbrains.march.market.core.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewOrder(@RequestHeader String username, @RequestBody ContactInfo contactInfo) {
        orderService.createNewOrder(username, contactInfo);
    }

    @GetMapping
    public List<OrderDto> getAllOrders(@RequestHeader String username) {
        return orderService.getAllOrders(username);
    }
}
