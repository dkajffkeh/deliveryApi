package com.barogo.domain.auth.controller.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static org.springframework.util.ObjectUtils.isEmpty;

@Getter
@NoArgsConstructor
@ToString
public class AuthFailPayload {

    private String failMessage;

    private static final String DEFAULT_MSG = "서명정보가 더이상 유효하지 않습니다.";
    public AuthFailPayload(String failMessage) {
        if(isEmpty(failMessage)) this.failMessage = DEFAULT_MSG;
        else this.failMessage = failMessage;
    }
}
