package com.barogo.domain.auth;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Slf4j
public class BarogoUserAuthInfo extends AbstractAuthenticationToken {

    private final String userId;

    @Getter
    private final String password;

    private final PrincipalUserDetails principalUserDetails;

    public BarogoUserAuthInfo(String userId, String password) {
        super(null);
        this.userId = userId;
        this.password = password;
        this.principalUserDetails = null;
    }

    public BarogoUserAuthInfo(PrincipalUserDetails principalUserDetails) {
        super(principalUserDetails.getAuthorities());
        this.userId = principalUserDetails.getUser().getUserId();
        this.password = principalUserDetails.getPassword();
        this.principalUserDetails = principalUserDetails;
    }

    @Override
    public Object getCredentials() {
        return this.userId;
    }

    @Override
    public Object getPrincipal() {
        return this.principalUserDetails;
    }
}
