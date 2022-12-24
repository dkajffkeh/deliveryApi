package com.barogo.domain.auth;

import com.barogo.common.exception.ApiServerException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static com.barogo.common.code.ResultCode.RESULT_1001;
import static com.barogo.common.regex.GlobalRegExContainer.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Password {

    @Column
    private String password;

    // 만족 조건 최소 갯수
    public static final int MIN_VALID_CONDITIONS = 3;

    public static final int PWD_MIN_LENGTH = 12;

    public static final int PWD_MAX_LENGTH = 18;

    public Password(String decodedPassword, PasswordEncoder passwordEncoder) {
        if(!isValidPwdForm(decodedPassword)) throw new ApiServerException(RESULT_1001);

        this.password = passwordEncoder.encode(decodedPassword);
    }

    public static boolean isValidPwdForm(String decodedPassword) {
        // 공백 및 유효하지 않은 특수문자 케이스
        if(hasInvalidChar(decodedPassword)) return false;

        // 3개 이상의 조건을 만족하는 Password
        return isAppropriatePwdForm(decodedPassword);
    }

    private static boolean validLength(String decodedPassword) {
        return decodedPassword.length() >= PWD_MIN_LENGTH && decodedPassword.length() <= PWD_MAX_LENGTH;
    }

    private static boolean isAppropriatePwdForm(String decodedPassword) {

        int detectNumber = hasNumber(decodedPassword)? 1 : 0;
        int detectLowerCase = hasLowerCase(decodedPassword)? 1 : 0;
        int detectUpperCase = hasUpperCase(decodedPassword)? 1 : 0;
        int detectSpecialChar = hasSpecialChar(decodedPassword)? 1 : 0;

        int detectedSum = detectNumber + detectLowerCase + detectUpperCase + detectSpecialChar;

        return detectedSum >= MIN_VALID_CONDITIONS;
    }
}
