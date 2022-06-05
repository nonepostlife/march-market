package ru.geekbrains.march.market.cart.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.geekbrains.march.market.api.CartDto;
import ru.geekbrains.march.market.cart.utils.Cart;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final CartItemConverter cartItemConverter;

    public CartDto entityToDto(Cart cart) {
        return new CartDto(cart.getItems().stream().map(cartItemConverter::entityToDto).collect(Collectors.toList()), cart.getTotalPrice());
    }
}
