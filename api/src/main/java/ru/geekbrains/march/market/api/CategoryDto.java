package ru.geekbrains.march.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class CategoryDto {
    @Schema(description = "ID категории", required = true, example = "1")
    private Long id;

    @Schema(description = "Название категории", required = true, example = "Food")
    private String title;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
