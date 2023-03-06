package com.parceldelivery.auth.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parceldelivery.auth.config.TestAppConfig;
import com.parceldelivery.auth.service.AuthService;
import com.parceldelivery.model.request.SigninApiRequest;
import com.parceldelivery.model.response.LoginResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestAppConfig.class})
public class AuthControllerTest {

    private static final String TOKEN = String.valueOf(UUID.randomUUID());

    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // region authenticateUser
    @Test
    public void test_authenticateUser_Success() throws Exception {
        // prepare
        SigninApiRequest signinApiRequest = SigninApiRequest.builder()
                .password("pass")
                .username("test")
                .build();
        LoginResponse loginResponse = LoginResponse.builder()
                .token(TOKEN)
                .build();
        when(authService.authenticateUser(signinApiRequest)).thenReturn(loginResponse);
        // execute & validate
        mockMvc.perform(post("/parcel-delivery/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signinApiRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", Matchers.is(TOKEN)));
    }
    // endregion
}
