package com.barogo.domain.auth.controller.payload;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.barogo.common.properties.JwtProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.barogo.common.properties.JwtProperties.BLANK_SPACE;
import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class LoginPayload {

    private String tokenType;

    private String accessToken;

    private String failMessage;

    private String username;

    private String userId;

    private Long id;

    public LoginPayload(String failMessage) {
        this.tokenType = null;
        this.accessToken = null;
        this.failMessage = failMessage;
        this.username = null;
        this.userId = null;
        this.id = null;
    }

    public LoginPayload(JwtProperties jwtProperties, HttpServletRequest request, HttpServletResponse response) {

        final Date expiresAt = new Date(System.currentTimeMillis() + jwtProperties.getRefreshTime());
        this.username = (String)request.getAttribute(jwtProperties.getClaim().getUsername());
        this.userId = (String)request.getAttribute(jwtProperties.getClaim().getUserId());
        this.id = (Long)request.getAttribute(jwtProperties.getClaim().getId());
        this.failMessage = "";
        this.tokenType = jwtProperties.getHeaderType();
        this.accessToken = tokenType + BLANK_SPACE + JWT.create()
                .withSubject(jwtProperties.getSubject()) // 제목
                .withExpiresAt(expiresAt) // 토큰 유효기간 설정
                .withClaim(jwtProperties.getClaim().getUsername(),this.username)
                .withClaim(jwtProperties.getClaim().getUserId(),this.userId)
                .withClaim(jwtProperties.getClaim().getId(),this.id)
                .sign(Algorithm.HMAC512(jwtProperties.getAlgorithm()));

        response.addHeader(jwtProperties.getCoreHeader(),jwtProperties.getHeaderTypeWithBlankSpace()+accessToken);
    }

}
