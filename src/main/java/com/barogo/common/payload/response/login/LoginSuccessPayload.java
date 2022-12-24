package com.barogo.common.payload.response.login;

import com.barogo.common.type.Role;
import com.barogo.domain.auth.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginSuccessPayload {

    @NotNull
    private String username;

    @NotNull
    private Role role;

    @NotNull
    private String accessToken;

    public LoginSuccessPayload(User user) {
        this.username = username;
        this.role = role;
    }
}
