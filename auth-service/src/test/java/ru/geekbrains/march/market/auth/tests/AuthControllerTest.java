package ru.geekbrains.march.market.auth.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void getProfileTest() throws Exception {
        mvc
                .perform(
                        get("/profile")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("username", "bob")

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("bob_johnson@gmail.com")));
    }
}
