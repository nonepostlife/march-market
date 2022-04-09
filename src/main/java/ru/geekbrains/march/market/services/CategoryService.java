package ru.geekbrains.march.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.march.market.converters.CategoryConverter;
import ru.geekbrains.march.market.dtos.CategoryDto;
import ru.geekbrains.march.market.dtos.ProductDto;
import ru.geekbrains.march.market.entities.Category;
import ru.geekbrains.march.market.entities.Product;
import ru.geekbrains.march.market.repositories.CategoryRepository;
import ru.geekbrains.march.market.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    public Optional<Category> findByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }

    public List<CategoryDto> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(categoryConverter::entityToDto).collect(Collectors.toList());
    }
}