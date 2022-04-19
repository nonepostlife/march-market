package ru.geekbrains.march.market.core.converters;

import org.springframework.stereotype.Component;
import ru.geekbrains.march.market.api.OrderItemDto;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.core.entities.OrderItem;
import ru.geekbrains.march.market.core.entities.Product;

@Component
public class OrderItemConverter {
    public OrderItemDto entityToDto(OrderItem item) {
        return new OrderItemDto(item.getId(), item.getOrder().getId(), item.getProduct().getId(),
                item.getProduct().getTitle(), item.getProduct().getCategory().getTitle(),
                item.getPricePerProduct(), item.getPrice(), item.getQuantity());
    }
}
