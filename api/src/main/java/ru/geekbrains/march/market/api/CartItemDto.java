package ru.geekbrains.march.market.api;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class CartItemDto {
    @Schema(description = "ID продукта", required = true, example = "1")
    private Long productId;

    @Schema(description = "Название продукта", required = true, example = "Milk")
    private String productTitle;

    @Schema(description = "Количество продуктов данного вида ", required = true, example = "2")
    private int quantity;

    @Schema(description = "Цена за 1 единицу данного продукта", required = true, example = "50")
    private BigDecimal pricePerProduct;

    @Schema(description = "Общая цена данного продукта", required = true, example = "100")
    private BigDecimal price;

    public CartItemDto() {
    }

    public CartItemDto(Long productId, String productTitle, int quantity, BigDecimal pricePerProduct, BigDecimal price) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
