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
import ru.geekbrains.march.market.api.ContactInfo;
import ru.geekbrains.march.market.api.OrderDto;
import ru.geekbrains.march.market.api.PageDto;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.core.converters.OrderConverter;
import ru.geekbrains.march.market.core.exceptions.AppError;
import ru.geekbrains.march.market.core.exceptions.ResourceNotFoundException;
import ru.geekbrains.march.market.core.services.OrderService;
import ru.geekbrains.march.market.core.utils.OrderStatus;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "Методы работы с заказами")
public class OrderController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @Operation(
            summary = "Запрос на создание нового заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Нельзя оформить заказ для пустой корзины", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    ),
                    @ApiResponse(
                            description = "Продукт в заказе не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewOrder(@RequestHeader @Parameter(description = "Имя пользователя", required = true) String username,
                               @RequestBody @Parameter(description = "Описание информации по доставке", required = true) ContactInfo contactInfo) {
        orderService.createNewOrder(username, contactInfo);
    }

    @Operation(
            summary = "Запрос на получение списка заказов для пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = OrderDto.class))
                    )
            }
    )
    @GetMapping
    public PageDto<OrderDto> getAllOrders(
            @RequestHeader String username,
            @RequestParam(name = "page", defaultValue = "0", required = false) @Parameter(description = "Номер страницы") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) @Parameter(description = "Кол-во выводимых элементов на странице") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) @Parameter(description = "Сортировка по имени столбца") String sortBy
    ) {
        if (page < 1) {
            page = 1;
        }
        return orderService.getAllOrders(username, page - 1, pageSize, sortBy);
    }

    @Operation(
            summary = "Запрос на получение заказа по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Заказ не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable @Parameter(description = "Идентификатор заказа", required = true) Long id) {
        return orderConverter.entityToDto(orderService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Заказ с id: " + id + " не найден")));
    }

    @Operation(
            summary = "Запрос на подтверждение получения заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/receive/{id}")
    public void confirmReceiptOrder(@PathVariable @Parameter(description = "Идентификатор заказа", required = true) Long id) {
        orderService.orderChangeStatus(id, OrderStatus.ORDER_DELIVERED);
    }
}
