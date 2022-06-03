package ru.geekbrains.march.market.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.march.market.api.PageDto;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.core.converters.ProductConverter;
import ru.geekbrains.march.market.core.exceptions.AppError;
import ru.geekbrains.march.market.core.exceptions.ResourceNotFoundException;
import ru.geekbrains.march.market.core.services.ProductService;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с продуктами")
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;

    @Operation(
            summary = "Запрос на получение списка продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping
    public PageDto<ProductDto> getAllProducts(
            @RequestParam(name = "page", defaultValue = "0", required = false) @Parameter(description = "Номер страницы") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) @Parameter(description = "Кол-во выводимых элементов на странице") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "title", required = false) @Parameter(description = "Сортировка по имени столбца") String sortBy,
            @RequestParam(name = "title_part", required = false) @Parameter(description = "Фильтр по части названия продукта") String titlePart,
            @RequestParam(name = "min_price", required = false) @Parameter(description = "Фильтр по минимальной цене продукта") Integer minPrice,
            @RequestParam(name = "max_price", required = false) @Parameter(description = "Фильтр по максимальной цене продукта") Integer maxPrice
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.findAll(page - 1, pageSize, sortBy, titlePart, minPrice, maxPrice);
    }

    @Operation(
            summary = "Запрос на получение продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id) {
        return productConverter.entityToDto(productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт с id: " + id + " не найден")));
    }

    @Operation(
            summary = "Запрос на создание нового продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно создан", responseCode = "201"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewProducts(@RequestBody @Parameter(description = "Описание продукта", required = true) ProductDto productDto) {
        productService.createNewProduct(productDto);
    }

    @Operation(
            summary = "Запрос на удаление продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable @Parameter(description = "Идентификатор продукта", required = true)  Long id) {
        productService.deleteById(id);
    }
}
