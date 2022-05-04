package ru.geekbrains.march.market.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class OrderDto {
    private Long id;
    private String username;
    private BigDecimal totalPrice;
    private List<OrderItemDto> items;
    private String deliverAddress;
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

