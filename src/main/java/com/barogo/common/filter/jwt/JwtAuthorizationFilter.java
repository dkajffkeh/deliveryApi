package com.barogo.common.filter.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.barogo.common.properties.JwtProperties;
import com.barogo.domain.auth.PrincipalUserDetails;
import com.barogo.domain.auth.User;
import com.barogo.domain.auth.repository.UserRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.barogo.common.utils.jwt.JwtClaimUtil.extractClaimByKey;
import static com.barogo.common.utils.jwt.JwtClaimUtil.extractDecodedToken;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Getter
    private final UserRepository userRepository;
    private final JwtProperties jwtProperties;

    private static final String TOKEN_EXPIRED_MSG = "토근 유효시간이 만료되었습니다.";

    private static final String TOKEN_USER_NOT_VALID_MSG = "토큰서명에 명시되어있는 사용자 정보가 더이상 유효하지 않습니다.";

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  UserRepository userRepository,
                                  JwtProperties jwtProperties
                                  ) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String jwtHeader = request.getHeader(jwtProperties.getCoreHeader());

        if(hasToken(jwtHeader)){

            String token = extractToken(jwtHeader);

            DecodedJWT decodedToken = extractDecodedToken(token,jwtProperties);

            if(isValidToken(decodedToken)){

                String userId = extractClaimByKey(decodedToken,jwtProperties.getClaim().getUserId());

                User userEntity = userRepository.findFirstByUserId(userId);

                // Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
                PrincipalUserDetails principalDetails = new PrincipalUserDetails(userEntity);

                // 비밀번호는 null 을 설정함 현재는 로그인 요청이 아님 username 이 null 이 아니기 때문에 정상적 서명이 이뤄졌다는것으로 간주함.
                // 서명이 정상일 경우 Authentication 객체를 만들어준다.
                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,null, principalDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication); // -> 강제로 시큐리티 세션에 접근하여 authentication 객체를 저장함

            }
        }

        chain.doFilter(request,response);
    }

    private String extractToken(String rawBearerToken){
        return rawBearerToken.replace(jwtProperties.getHeaderTypeWithBlankSpace(),"");
    }

    private boolean hasToken(String jwtHeader){
        return jwtHeader != null && jwtHeader.startsWith(jwtProperties.getHeaderType());
    }

    private boolean isValidToken(DecodedJWT decodedToken){
        return decodedToken.getSubject().equals(jwtProperties.getSubject());
    }

}
