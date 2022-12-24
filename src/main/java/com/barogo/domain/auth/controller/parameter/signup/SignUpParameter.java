package com.barogo.domain.auth.controller.parameter.signup;

import com.barogo.common.validation.ValidPwd;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static com.barogo.domain.auth.Password.*;
import static com.barogo.domain.auth.User.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpParameter {

    @NotEmpty(message = "{user_id.not-empty}")
    @Size(min = MIN_USER_ID_LENGTH, max = MAX_USER_ID_LENGTH)
    private String userId;

    @NotEmpty(message = "{password.not-empty}")
    @Size(min = PWD_MIN_LENGTH, max = PWD_MAX_LENGTH)
    @ValidPwd(message = "{invalid.pwd.regex}")
    private String password;

    @NotEmpty(message = "{username.not-empty}")
    @Size(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
    private String username;


}
