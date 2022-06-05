package ru.geekbrains.march.market.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.geekbrains.march.market.api.OrderDto;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.core.entities.Order;
import ru.geekbrains.march.market.core.entities.Product;
import ru.geekbrains.march.market.core.utils.OrderStatus;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public OrderDto entityToDto(Order o) {
        return new OrderDto(o.getId(), o.getUsername(), o.getStatus(), OrderStatus.valueOf(o.getStatus()).getTitle(), o.getTotalPrice(),
                o.getItems().stream().map(orderItemConverter::entityToDto).collect(Collectors.toList()),
                o.getOrderDetails().getAddress(), o.getCreatedAt());
    }
}
