package ru.geekbrains.march.market.cart.converters;

import org.springframework.stereotype.Component;
import ru.geekbrains.march.market.api.CartItemDto;
import ru.geekbrains.march.market.cart.utils.CartItem;

@Component
public class CartItemConverter {
    public CartItemDto entityToDto(CartItem item) {
        return new CartItemDto(item.getProductId(), item.getProductTitle(), item.getQuantity(), item.getPricePerProduct(), item.getPrice());
    }
}
