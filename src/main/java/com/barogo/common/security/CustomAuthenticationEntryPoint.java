package com.barogo.common.security;

import com.barogo.common.code.ResultCode;
import com.barogo.common.payload.response.ResponseData;
import com.barogo.domain.auth.controller.payload.AuthFailPayload;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.barogo.common.utils.HttpServletRequestUtils.MESSAGE_KEY;
import static com.barogo.common.utils.HttpServletRequestUtils.RESULT_CODE_KEY;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // TODO 토는 인증 만료 처리 규격화
    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        log.info("Unauthorized");

        response.setStatus(SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");

        AuthFailPayload authFailPayload = new AuthFailPayload((String)request.getAttribute(MESSAGE_KEY));

        ResultCode resultCode = (ResultCode) request.getAttribute(RESULT_CODE_KEY);

        ResponseData<AuthFailPayload> payload = ResponseData.success(resultCode,authFailPayload);

        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(payload));

    }
}
