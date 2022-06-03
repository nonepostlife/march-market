package ru.geekbrains.march.market.cart.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.march.market.api.CartDto;
import ru.geekbrains.march.market.api.StringResponse;
import ru.geekbrains.march.market.cart.converters.CartConverter;
import ru.geekbrains.march.market.cart.execptions.AppError;
import ru.geekbrains.march.market.cart.services.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Корзина", description = "Методы работы с корзиной")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @Operation(
            summary = "Запрос на получение гостевого id корзины",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    )
            }
    )
    @GetMapping("/generate_id")
    public StringResponse generateGuestCartId() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @Operation(
            summary = "Запрос на получение текущей корзины",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CartDto.class))
                    )
            }
    )
    @GetMapping("/{guestCartId}")
    public CartDto getCurrentCart(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username,
                                  @PathVariable @Parameter(description = "Идентификатор гостя", required = true) String guestCartId) {
        String currentCartId = selectCartId(username, guestCartId);
        return cartConverter.entityToDto(cartService.getCurrentCart(currentCartId));
    }

    @Operation(
            summary = "Запрос на добавление продукта в корзину",
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
    @GetMapping("/{guestCartId}/add/{productId}")
    public void addProductToCart(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username,
                                 @PathVariable @Parameter(description = "Идентификатор гостя", required = true) String guestCartId,
                                 @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long productId) {
        String currentCartId = selectCartId(username, guestCartId);
        cartService.addToCart(currentCartId, productId);
    }

    @Operation(
            summary = "Запрос на очистку корзины",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{guestCartId}/clear")
    public void clearCurrentCart(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username,
                                 @PathVariable @Parameter(description = "Идентификатор гостя", required = true) String guestCartId) {
        String currentCartId = selectCartId(username, guestCartId);
        cartService.clearCart(currentCartId);
    }

    @Operation(
            summary = "Запрос на удаление продукта из корзины",
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
    @GetMapping("/{guestCartId}/delete/{productId}")
    public void deleteProductFromCart(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username,
                                      @PathVariable @Parameter(description = "Идентификатор гостя", required = true) String guestCartId,
                                      @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long productId) {
        String currentCartId = selectCartId(username, guestCartId);
        cartService.deleteProductFromCart(currentCartId, productId);
    }

    @Operation(
            summary = "Запрос на удаление экземпляра продукта из корзины",
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
    @GetMapping("/{guestCartId}/remove/{productId}")
    public void removeItemFromCart(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username,
                                   @PathVariable @Parameter(description = "Идентификатор гостя", required = true) String guestCartId,
                                   @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long productId) {
        String currentCartId = selectCartId(username, guestCartId);
        cartService.removeItemFromCart(currentCartId, productId);
    }

    @Operation(
            summary = "Объединение гостевой корзины и корзины пользователя при авторизации",
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
    @GetMapping("/{guestCartId}/merge")
    public void mergeGuestCartWithUserCart(@RequestHeader @Parameter(description = "Имя пользователя", required = true) String username,
                                           @PathVariable @Parameter(description = "Идентификатор гостя", required = true) String guestCartId) {
        cartService.mergeCart(username, guestCartId);
        cartService.clearCart(guestCartId);
    }

    private String selectCartId(String username, String guestCartId) {
        if (username != null) {
            return username;
        }
        return guestCartId;
    }
}
