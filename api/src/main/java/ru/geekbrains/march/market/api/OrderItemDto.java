package ru.geekbrains.march.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class OrderItemDto {
    @Schema(description = "ID товара в заказе", required = true, example = "1")
    private Long id;

    @Schema(description = "ID заказа", required = true, example = "1")
    private Long orderId;

    @Schema(description = "ID продукта", required = true, example = "1")
    private Long productId;

    @Schema(description = "Название продукта", required = true, example = "Milk")
    private String productTitle;

    @Schema(description = "Категория продукта", required = true, example = "Food")
    private String productCategoryTitle;

    @Schema(description = "Цена за 1 единицу данного продукта", required = true, example = "50")
    private BigDecimal pricePerProduct;

    @Schema(description = "Общая цена данного продукта", required = true, example = "100")
    private BigDecimal price;

    @Schema(description = "Количество продуктов данного вида ", required = true, example = "2")
    private int quantity;

    public OrderItemDto() {
    }

    public OrderItemDto(Long id, Long orderId, Long productId, String productTitle, String productCategoryTitle, BigDecimal pricePerProduct, BigDecimal price, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productTitle = productTitle;
        this.productCategoryTitle = productCategoryTitle;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductCategoryTitle() {
        return productCategoryTitle;
    }

    public void setProductCategoryTitle(String productCategoryTitle) {
        this.productCategoryTitle = productCategoryTitle;
    }
}
