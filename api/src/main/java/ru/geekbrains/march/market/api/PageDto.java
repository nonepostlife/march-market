package ru.geekbrains.march.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class PageDto<E> {
    @Schema(description = "Список товаров на странице")
    private List<E> items;

    @Schema(description = "Номер страницы", required = true, example = "1")
    private int page;

    @Schema(description = "Общее количество страниц", required = true, example = "3")
    private int totalPages;

    @Schema(description = "Общее количество продуктов", required = true, example = "13")
    private long totalElements;

    @Schema(description = "Количество товаров на странице", required = true, example = "5")
    private int numberOfElements;

    @Schema(description = "Количество выводимых товаров на странице", required = true, example = "5")
    private int pageSize;

    public PageDto(List<E> items, int page, int totalPages, long totalElements, int numberOfElements, int pageSize) {
        this.items = items;
        this.page = page;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.numberOfElements = numberOfElements;
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}
