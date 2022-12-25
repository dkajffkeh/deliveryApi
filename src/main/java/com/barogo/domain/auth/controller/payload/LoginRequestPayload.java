package com.barogo.domain.auth.controller.payload;

import com.barogo.common.code.ResultCode;
import com.barogo.common.exception.ApiServerException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.util.ObjectUtils;

import static com.barogo.common.code.ResultCode.*;
import static com.barogo.common.regex.GlobalRegExContainer.hasBlank;
import static com.barogo.common.regex.GlobalRegExContainer.hasSpecialChar;
import static org.springframework.util.ObjectUtils.isEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequestPayload {

    private String userId;
    private String password;

    public LoginRequestPayload(String userId, String password) {
        checkPropertiesCondition(userId,password);
        this.userId = userId;
        this.password = password;
    }

    public void checkPropertiesCondition(String userId,String password) {
        if(hasBlank(userId)) throw new ApiServerException(RESULT_4017);
        if(hasSpecialChar(userId)) throw new ApiServerException(RESULT_4018);
        if(isEmpty(userId)) throw new ApiServerException(RESULT_4010);
        if(isEmpty(password)) throw new ApiServerException(RESULT_4011);
    }
}
