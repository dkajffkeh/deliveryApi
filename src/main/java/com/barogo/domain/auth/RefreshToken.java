package com.barogo.domain.auth;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class RefreshToken {
    @Column(name = "refresh_token")
    private String refreshToken;

    protected RefreshToken() {
    }

    public RefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
