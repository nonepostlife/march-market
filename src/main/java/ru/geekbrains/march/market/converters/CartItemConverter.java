package ru.geekbrains.march.market.converters;

import org.springframework.stereotype.Component;
import ru.geekbrains.march.market.dtos.CartDto;
import ru.geekbrains.march.market.dtos.CartItemDto;
import ru.geekbrains.march.market.utils.Cart;
import ru.geekbrains.march.market.utils.CartItem;

@Component
public class CartItemConverter {
    public CartItemDto entityToDto(CartItem item) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductTitle(item.getProductTitle());
        cartItemDto.setPrice(item.getPrice());
        cartItemDto.setQuantity(item.getQuantity());
        cartItemDto.setProductId(item.getProductId());
        cartItemDto.setPricePerProduct(item.getPricePerProduct());
        return cartItemDto;
    }
}
