package com.picpay.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picpay.security.authentication.dto.LoginFormDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    public AuthenticationControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    private String asJson(Object dto) throws IOException {
        return new ObjectMapper().writeValueAsString(dto);
    }

    @Test
    @Sql("/db/create-auth-user.sql")
    void test_authenticate_with_validUser_should_return200() throws Exception {
        LoginFormDTO loginForm = LoginFormDTO.newBuilder()
                .withPassword("12345")
                .withUsername("admin")
                .build();
        URI uri = new URI("/auth");
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(asJson(loginForm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void test_authenticate_with_invalidUser_should_return400() throws Exception {
        LoginFormDTO loginForm = LoginFormDTO.newBuilder()
                .withPassword("123")
                .withUsername("Leandro")
                .build();

        URI uri = new URI("/auth");
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(asJson(loginForm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(400)));
    }

}