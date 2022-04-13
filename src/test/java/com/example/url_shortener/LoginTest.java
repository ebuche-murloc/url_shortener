package com.example.url_shortener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {
    @Autowired
    private MockMvc mockMvc; // позволяет тестировать контроллеры без запуска http сервера


    @Test
    public void contextLoads() throws Exception {
        this.mockMvc.perform(get("/"))// делает запрос
                .andDo(print()) //выводит результат в консоль
                .andExpect(status().isOk()) // ожидаем статус запроса 200
                .andExpect(content().string(containsString("Hi, ")));
    }

    @Test
    public void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(status().is3xxRedirection()) // проверяем что нас перенаправляет
                .andExpect(redirectedUrl("http://localhost/login"));
    }
    @Test
    public void correctLoginTest() throws Exception {
        this.mockMvc.perform(formLogin().user("admin").password("admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
