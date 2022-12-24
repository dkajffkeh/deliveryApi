package com.barogo.domain.auth.controller.payload;

import com.barogo.domain.auth.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpSuccessPayload {

    private String userId;

    private String username;

    private LocalDateTime signUpSuccessDate;

    public SignUpSuccessPayload(User user){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.signUpSuccessDate = user.getRegDtm();
    }
}
