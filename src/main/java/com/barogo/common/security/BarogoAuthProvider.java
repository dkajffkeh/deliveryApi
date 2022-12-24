package com.barogo.common.security;

import com.barogo.common.code.ResultCode;
import com.barogo.domain.auth.BarogoUserAuthInfo;
import com.barogo.domain.auth.PrincipalUserDetails;
import com.barogo.domain.auth.application.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BarogoAuthProvider implements AuthenticationProvider {

    private final PrincipalDetailsService principalDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BarogoUserAuthInfo barogoUserAuthInfo = (BarogoUserAuthInfo) authentication;
        String userId = (String) barogoUserAuthInfo.getCredentials();

        PrincipalUserDetails principalUserDetails = (PrincipalUserDetails) principalDetailsService.loadUserByUsername(userId);

        if (!passwordEncoder.matches(barogoUserAuthInfo.getPassword(), principalUserDetails.getPassword())) {
            throw new BadCredentialsException(ResultCode.RESULT_4003.getResultMessage());
        }

        final BarogoUserAuthInfo finalAuthInfo = new BarogoUserAuthInfo(principalUserDetails);
        finalAuthInfo.setAuthenticated(true);
        return finalAuthInfo;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BarogoUserAuthInfo.class.isAssignableFrom(authentication);
    }
}
