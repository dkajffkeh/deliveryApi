package com.barogo.common.filter.jwt;

import com.barogo.common.exception.ApiServerException;
import com.barogo.common.properties.JwtProperties;
import com.barogo.common.security.BarogoAuthProvider;
import com.barogo.domain.auth.BarogoUserAuthInfo;
import com.barogo.domain.auth.PrincipalUserDetails;
import com.barogo.domain.auth.controller.payload.LoginRequestPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.barogo.common.code.ResultCode.RESULT_4000;

// username
// password 인증처리 Filter
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProperties jwtProperties;

    private final BarogoAuthProvider barogoAuthProvider;
    // login 시도할 경우 동작하는 Filter
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("[Login Requested]");

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            LoginRequestPayload loginRequestPayload = objectMapper.readValue(
                    request.getInputStream(),
                    LoginRequestPayload.class);

            loginRequestPayload.checkPropertiesCondition(
                    loginRequestPayload.getUserId()
                    ,loginRequestPayload.getPassword()
            );

            Authentication authentication = allocateAuthToken(
                    loginRequestPayload.getUserId(),
                    loginRequestPayload.getPassword());

            return getAuthenticationManager().authenticate(authentication);

        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiServerException(RESULT_4000);
        }
    }

    public Authentication allocateAuthToken(String userId, String password) {
        BarogoUserAuthInfo barogoUserAuthInfo = new BarogoUserAuthInfo(userId, password);
        SecurityContextHolder.getContext().setAuthentication(barogoUserAuthInfo);
        return SecurityContextHolder.getContext().getAuthentication();
    }

    // 인증이 완료 되었을경우 JWT 토큰을 만들어 return.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("[Login Request Success]");
       PrincipalUserDetails principalDetails = (PrincipalUserDetails) authResult.getPrincipal();

        request.setAttribute(jwtProperties.getClaim().getId(), principalDetails.getUser().getId());
        request.setAttribute(jwtProperties.getClaim().getUsername(),principalDetails.getUser().getUsername());
        request.setAttribute(jwtProperties.getClaim().getUserId(),principalDetails.getUser().getUserId());

        request.getRequestDispatcher("/users/token").forward(request,response);
    }
}
