package com.barogo.domain.auth.controller.payload;

import com.barogo.common.utils.DateTimeUtils;
import com.barogo.domain.auth.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

import static com.barogo.common.utils.DateTimeUtils.convertLocalDateTimeToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpSuccessPayload {

    private String userId;

    private String username;

    private String signUpDate;

    public SignUpSuccessPayload(User user){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.signUpDate = convertLocalDateTimeToString(user.getRegDtm());
    }
}
