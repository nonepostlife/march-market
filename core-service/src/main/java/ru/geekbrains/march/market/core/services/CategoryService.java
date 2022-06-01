package ru.geekbrains.march.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.geekbrains.march.market.api.CategoryDto;
import ru.geekbrains.march.market.core.converters.CategoryConverter;
import ru.geekbrains.march.market.core.entities.Category;
import ru.geekbrains.march.market.core.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    private final RedisTemplate<String, Object> redisTemplate;

    public Optional<Category> findByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }

    public List<CategoryDto> findAll() {
        if (!redisTemplate.hasKey("categoryList")) {
            redisTemplate.opsForValue().set("categoryList", categoryRepository.findAll().stream().map(categoryConverter::entityToDto).collect(Collectors.toList()));
        }
        return (List<CategoryDto>) redisTemplate.opsForValue().get("categoryList");
    }
}