package ru.geekbrains.march.market.core.converters;

import org.springframework.stereotype.Component;
import ru.geekbrains.march.market.api.CategoryDto;
import ru.geekbrains.march.market.core.entities.Category;

@Component
public class CategoryConverter {
    public CategoryDto entityToDto(Category c) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(c.getId());
        categoryDto.setTitle(c.getTitle());
        return categoryDto;
    }
}
