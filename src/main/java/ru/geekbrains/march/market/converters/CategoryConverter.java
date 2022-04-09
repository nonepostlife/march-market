package ru.geekbrains.march.market.converters;

import org.springframework.stereotype.Component;
import ru.geekbrains.march.market.dtos.CategoryDto;
import ru.geekbrains.march.market.dtos.ProductDto;
import ru.geekbrains.march.market.entities.Category;
import ru.geekbrains.march.market.entities.Product;

@Component
public class CategoryConverter {
    public CategoryDto entityToDto(Category c) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(c.getId());
        categoryDto.setTitle(c.getTitle());
        return categoryDto;
    }
}
