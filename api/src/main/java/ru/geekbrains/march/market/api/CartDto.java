package ru.geekbrains.march.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

public class CartDto {
    @Schema(description = "Список продуктов в корзине", required = true, implementation = CartItemDto.class)
    private List<CartItemDto> items;

    @Schema(description = "Суммарная стоимость продуктов в корзине", required = true, example = "21412.20")
    private BigDecimal totalPrice;

    public CartDto() {
    }

    public CartDto(List<CartItemDto> items, BigDecimal totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
