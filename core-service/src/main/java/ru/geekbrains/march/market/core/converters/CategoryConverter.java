package ru.geekbrains.march.market.core.converters;

import org.springframework.stereotype.Component;
import ru.geekbrains.march.market.api.CategoryDto;
import ru.geekbrains.march.market.core.entities.Category;

@Component
public class CategoryConverter {
    public CategoryDto entityToDto(Category c) {
        return new CategoryDto(c.getId(), c.getTitle());
    }
}
