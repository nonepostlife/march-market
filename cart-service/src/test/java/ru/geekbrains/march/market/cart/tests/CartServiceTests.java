package ru.geekbrains.march.market.cart.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.cart.integrations.ProductServiceIntegration;
import ru.geekbrains.march.market.cart.services.CartService;
import ru.geekbrains.march.market.cart.utils.Cart;

import java.math.BigDecimal;

@SpringBootTest(classes = CartService.class)
public class CartServiceTests {
    @Autowired
    private CartService cartService;

    @MockBean
    private ProductServiceIntegration productService;

    @Test
    public void cartOperationTest() {
        ProductDto product1 = new ProductDto(1L, "Апельсины", BigDecimal.valueOf(130.0), "Еда");
        ProductDto product2 = new ProductDto(2L, "Яблоки", BigDecimal.valueOf(100.0), "Еда");
        ProductDto product3 = new ProductDto(3L, "Манго", BigDecimal.valueOf(250.0), "Еда");
        Mockito.doReturn(product1)
                .when(productService)
                .findById(1L);
        Mockito.doReturn(product2)
                .when(productService)
                .findById(2L);
        Mockito.doReturn(product3)
                .when(productService)
                .findById(3L);

        cartService.addToCart("bob", 1L);
        cartService.addToCart("bob", 1L);
        cartService.addToCart("bob", 2L);
        cartService.addToCart("bob", 2L);
        cartService.removeItemFromCart("bob", 1L);
        cartService.deleteProductFromCart("bob", 1L);
        Cart cart = cartService.getCurrentCart("bob");
        Assertions.assertEquals(1, cart.getItems().size());
        Assertions.assertEquals(2, cart.getItems().get(0).getQuantity());
        Assertions.assertEquals(BigDecimal.valueOf(200.0), cart.getTotalPrice());

        cartService.clearCart("bob");
        Cart cart2 = cartService.getCurrentCart("bob");
        Assertions.assertEquals(0, cart2.getItems().size());
        Assertions.assertEquals(BigDecimal.valueOf(0), cart2.getTotalPrice());
    }
}
