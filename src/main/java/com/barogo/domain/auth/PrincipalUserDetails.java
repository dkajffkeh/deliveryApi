package com.barogo.domain.auth;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.util.ObjectUtils.isEmpty;

@Getter
public class PrincipalUserDetails implements UserDetails {

    private static final String USER_NOT_VALID = "서명정보에 명시되어있는 사용자가 더이상 유효하지 않습니다.";

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(()-> user.getRole().getRole());
        return authorities;
    }

    public PrincipalUserDetails(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.passwordDirectGet();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}