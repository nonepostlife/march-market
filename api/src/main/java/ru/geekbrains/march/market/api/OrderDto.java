package ru.geekbrains.march.market.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class OrderDto {
    private Long id;
    private Long userId;
    private BigDecimal totalPrice;
    private List<OrderItemDto> items;
    private String createdAt;

    public OrderDto() {
    }

    public OrderDto(Long id, Long userId, BigDecimal totalPrice, List<OrderItemDto> items, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.items = items;
        this.createdAt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(createdAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(createdAt);;
    }
}

