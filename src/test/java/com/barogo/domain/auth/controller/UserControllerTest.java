package com.barogo.domain.auth.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.barogo.common.properties.JwtProperties;
import com.barogo.domain.auth.controller.parameter.signup.SignUpParameter;
import com.barogo.domain.auth.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

import static com.barogo.common.code.ResultCode.RESULT_1006;
import static com.barogo.common.code.ResultCode.RESULT_1007;
import static com.barogo.common.utils.jwt.JwtClaimUtil.extractClaimByKey;
import static com.barogo.common.utils.jwt.JwtClaimUtil.extractDecodedToken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

    private static final SignUpParameter TEST_PARAM =
            new SignUpParameter("barogo12345","A1234#gase12","홍길동");

    private static final SignUpParameter DUPLICATED_PARAM =
            new SignUpParameter("barogo12345","Barogo1234567","청길동");

    private final ObjectMapper objectMapper = new ObjectMapper();

    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;

    @Test
    @DisplayName("회원가입 정상 테스트")
    @Order(0)
    void signUpSuccessTest() throws Exception {

        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(TEST_PARAM))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
            .andExpect(jsonPath("resultCode").value("0000"))
            .andExpect(jsonPath("resultMessage").value("성공"))
            .andExpect(jsonPath("resultData.userId").value("barogo12345"));

    }

    @Test
    @DisplayName("중복 회원 테스트")
    void userConflictTest() throws Exception {

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(DUPLICATED_PARAM))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(jsonPath("resultCode").value("9999"))
                .andExpect(jsonPath("resultMessage").value("실패"))
                .andExpect(jsonPath("resultData.message").value("이미 존재하는 사용자입니다."));

    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginTest() throws Exception {

        MvcResult loginResult = mockMvc.perform(post("/login")
                        .content(objectMapper.writeValueAsString(TEST_PARAM))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(forwardedUrl("/users/token"))
                .andExpect(status().isOk())
                .andReturn();

        HttpServletRequest request = loginResult.getRequest();

        MvcResult forwardedLoginResult = mockMvc.perform(post(Objects.requireNonNull(loginResult.getResponse().getForwardedUrl()))
                        .requestAttr(jwtProperties.getClaim().getId(), request.getAttribute(jwtProperties.getClaim().getId()))
                        .requestAttr(jwtProperties.getClaim().getUsername(), request.getAttribute(jwtProperties.getClaim().getUsername())
                        )).andDo(print())
                .andReturn();

        HttpServletResponse redirectedResponse = forwardedLoginResult.getResponse();

        // token 받아옴.
        String token = redirectedResponse.getHeader(jwtProperties.getCoreHeader());
        token = extractToken(token);
        DecodedJWT decodedToken = extractDecodedToken(token,jwtProperties);
        String username = extractClaimByKey(decodedToken,jwtProperties.getClaim().getUsername());

        Assertions.assertNotNull(token);
        assertEquals(username,"홍길동");
    }

    @Test
    @DisplayName("로그인 실패 케이스 테스트 (비밀번호 불일치) ")
    void loginFailPwdTest() throws Exception {

        final SignUpParameter TEST_FAIL_PARAM =
                new SignUpParameter("barogo12345","A1234#ggse12","홍길동");

        MvcResult loginResult = mockMvc.perform(post("/login")
                        .content(objectMapper.writeValueAsString(TEST_FAIL_PARAM))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(forwardedUrl("/users/login/fail"))
                .andExpect(status().isOk())
                .andReturn();

        HttpServletRequest request = loginResult.getRequest();

        assertEquals(request.getAttribute("resultCode"),RESULT_1007.getResultCode());
        assertEquals(request.getAttribute("message"),RESULT_1007.getResultMessage());

    }

    @Test
    @DisplayName("로그인 실패 케이스 테스트 (아이디 불일치) ")
    void loginFailIdTest() throws Exception {

        final SignUpParameter TEST_FAIL_PARAM =
                new SignUpParameter("barogo123455","A1234#gase12","홍길동");

        MvcResult loginResult = mockMvc.perform(post("/login")
                        .content(objectMapper.writeValueAsString(TEST_FAIL_PARAM))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(forwardedUrl("/users/login/fail"))
                .andExpect(status().isOk())
                .andReturn();

        HttpServletRequest request = loginResult.getRequest();

        assertEquals(request.getAttribute("resultCode"),RESULT_1006.getResultCode());
        assertEquals(request.getAttribute("message"),RESULT_1006.getResultMessage());

    }

    private String extractToken(String rawBearerToken){
        return rawBearerToken.replace(jwtProperties.getHeaderTypeWithBlankSpace(),"");
    }
}