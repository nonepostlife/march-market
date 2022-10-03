package ru.geekbrains.march.market.core.utils;

public enum OrderStatus {
    ORDER_WAIT_TO_PAYMENT("Заказ ожидает оплаты"),
    ORDER_HAS_BEEN_PAID("Заказ оплачен"),
    ORDER_ON_THE_WAY("Заказ в пути"),
    ORDER_DELIVERED("Заказ доставлен");


    private String title;

    OrderStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
