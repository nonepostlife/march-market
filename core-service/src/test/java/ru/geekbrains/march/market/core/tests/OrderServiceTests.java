package ru.geekbrains.march.market.core.tests;

import org.apache.coyote.Request;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.march.market.api.CartDto;
import ru.geekbrains.march.market.api.CartItemDto;
import ru.geekbrains.march.market.core.converters.OrderConverter;
import ru.geekbrains.march.market.core.entities.Category;
import ru.geekbrains.march.market.core.entities.Product;
import ru.geekbrains.march.market.core.integrations.CartServiceIntegration;
import ru.geekbrains.march.market.core.repositories.OrderRepository;
import ru.geekbrains.march.market.core.services.OrderService;
import ru.geekbrains.march.market.core.services.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = OrderService.class)
@ActiveProfiles("test")
public class OrderServiceTests {
    @Autowired
    private OrderService orderService;
    @MockBean
    private ProductService productService;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private OrderConverter orderConverter;
    @MockBean
    private CartServiceIntegration cartServiceIntegration;

    @Test
    public void getAllOrdersTest() {
        String username = "bob";
        orderService.getAllOrders(username, 0, 5, "id");
        Mockito.verify(orderRepository, Mockito.times(1)).findAllByUsername(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    public void createNewOrderTest() {
        CartDto cart = new CartDto();
        List<CartItemDto> cartItems = new ArrayList<>();
        cartItems.add(new CartItemDto(1L, "Хлеб", 1, BigDecimal.valueOf(25), BigDecimal.valueOf(25)));
        cartItems.add(new CartItemDto(2L, "Молоко", 2, BigDecimal.valueOf(50), BigDecimal.valueOf(100)));
        cart.setItems(cartItems);
        cart.setTotalPrice(BigDecimal.valueOf(125));
        Mockito.doReturn(cart)
                .when(cartServiceIntegration)
                .getCurrentUserCart("bob");

        Category category = new Category();
        category.setId(1L);
        category.setTitle("Еда");
        category.setProducts(Collections.emptyList());
        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Хлеб");
        product1.setCategory(category);
        product1.setPrice(BigDecimal.valueOf(25));
        Product product2 = new Product();
        product1.setId(1L);
        product1.setTitle("Молоко");
        product1.setCategory(category);
        product1.setPrice(BigDecimal.valueOf(50));

        Mockito.doReturn(Optional.of(product1))
                .when(productService)
                .findById(1L);
        Mockito.doReturn(Optional.of(product2))
                .when(productService)
                .findById(2L);

        String username = "bob";
        orderService.createNewOrder(username, null);
        Mockito.verify(orderRepository, Mockito.times(1)).save(ArgumentMatchers.any());
    }
}
