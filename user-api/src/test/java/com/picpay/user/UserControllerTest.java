package com.picpay.user;

import com.picpay.security.authentication.domain.RoleNameConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StreamUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    private MockMvc mockMvc;
    private final Resource validUser = new ClassPathResource("json/validUsers.json");

    @Autowired
    public UserControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    private String asJson(Resource resource) throws IOException {
        return StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
    }

    @Test
    @WithMockUser
    void should_find_by_keyword() throws Exception {
        URI uri = new URI("/users?keyword=leandro&pageNumber=0");
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    @WithMockUser(roles = {RoleNameConstants.INTEGRATION})
    @Sql("/db/create-user.sql")
    void should_create_users() throws Exception {
        URI uri = new URI("/users");
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(asJson(validUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(201)));
    }

}