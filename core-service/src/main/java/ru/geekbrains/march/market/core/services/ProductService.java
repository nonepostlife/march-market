package ru.geekbrains.march.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.core.converters.ProductConverter;
import ru.geekbrains.march.market.core.exceptions.ResourceNotFoundException;
import ru.geekbrains.march.market.core.entities.Product;
import ru.geekbrains.march.market.core.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductConverter productConverter;

    public Page<ProductDto> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<ProductDto> pagedResult = productRepository.findAll(paging).map(productConverter::entityToDto);
        return pagedResult;
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public void createNewProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryService.findByTitle(productDto.getCategoryTitle().trim()).orElseThrow(() -> new ResourceNotFoundException("Категория с названием: " + productDto.getCategoryTitle() + " не найдена")));
        productRepository.save(product);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
}
