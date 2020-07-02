package com.gloryjewel.book.web;

import com.gloryjewel.book.config.auth.SecurityConfig;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = HelloController.class,
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
class HelloControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(roles = "USER")
    @Test
    void hello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    @WithMockUser(roles = "USER")
    @Test
    void helloDto() throws Exception {
        mockMvc.perform(get("/hello/dto")
                            .param("name","test")
                            .param("amount", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("test")))
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)));
    }
}