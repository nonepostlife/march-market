package ru.geekbrains.march.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class OrderDto {
    @Schema(description = "ID заказа", required = true, example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", required = true, example = "Bob")
    private String username;

    @Schema(description = "Суммарная стоимость продуктов в заказе", required = true, example = "21412.20")
    private BigDecimal totalPrice;

    @Schema(description = "Список товаров в заказе", required = true, implementation = OrderItemDto.class)
    private List<OrderItemDto> items;

    @Schema(description = "Адрес доставки заказа", required = true, example = "680020, Россия, Хабаровский край, Хабаровск, ул Ленина, Дом 1, Квартира 1")
    private String deliverAddress;

    @Schema(description = "Дата создания заказа", required = true, example = "3 июн. 2022 г., 15:39:16")
    private String createdAt;

    public OrderDto() {
    }

    public OrderDto(Long id, String username, BigDecimal totalPrice, List<OrderItemDto> items, String deliverAddress, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.totalPrice = totalPrice;
        this.items = items;
        this.deliverAddress = deliverAddress;
        this.createdAt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(createdAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }
}

