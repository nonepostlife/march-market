package ru.geekbrains.march.market.cart.tests;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.cart.integrations.ProductServiceIntegration;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductServiceIntegration productService;

    @Test
    public void getCurrentCartTest() throws Exception {
        mvc
                .perform(
                        get("/api/v1/cart")
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.totalPrice", is(230.00)));
    }

    @Test
    public void workWithCartTest() throws Exception {
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

        mvc
                .perform(
                        get("/api/v1/cart/add/1").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
        mvc
                .perform(
                        get("/api/v1/cart/add/2").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
        mvc
                .perform(
                        get("/api/v1/cart/add/2").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
        mvc
                .perform(
                        get("/api/v1/cart/add/3").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
        mvc
                .perform(
                        get("/api/v1/cart/add/3").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
        mvc
                .perform(
                        get("/api/v1/cart/remove/2").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
        mvc
                .perform(
                        get("/api/v1/cart/delete/3").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
