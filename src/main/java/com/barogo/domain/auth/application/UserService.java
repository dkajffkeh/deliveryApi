package com.barogo.domain.auth.application;

import com.barogo.common.code.ResultCode;
import com.barogo.common.exception.UserIdAlreadyExistException;
import com.barogo.common.properties.JwtProperties;
import com.barogo.domain.auth.controller.parameter.signup.SignUpParameter;
import com.barogo.domain.auth.controller.payload.LoginPayload;
import com.barogo.domain.auth.controller.payload.SignUpSuccessPayload;
import com.barogo.domain.auth.User;
import com.barogo.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;

import static com.barogo.common.utils.HttpServletRequestUtils.extractMessage;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProperties jwtProperties;

    private final PasswordEncoder passwordEncoder;

    public SignUpSuccessPayload signUp(SignUpParameter signUpParameter) {
        validateDuplicateUser(signUpParameter.getUserId());
        User user = new User(signUpParameter,passwordEncoder);
        user = userRepository.save(user);
        return new SignUpSuccessPayload(user);
    }

    private void validateDuplicateUser(String userId) {
        final User user = userRepository.findFirstByUserId(userId);
        if(!isEmpty(user)) throw new UserIdAlreadyExistException(ResultCode.RESULT_4009);
    }

    public LoginPayload loginSuccessHandler(HttpServletRequest request, HttpServletResponse response) {
        LoginPayload loginPayload = new LoginPayload(jwtProperties,request,response);
        response.addHeader(jwtProperties.getCoreHeader(),
                jwtProperties.getHeaderTypeWithBlankSpace()+loginPayload.getAccessToken());
        return loginPayload;
    }

    public LoginPayload loginFailHandler(HttpServletRequest request){
        String message = extractMessage(request,"message");
        return new LoginPayload(message);
    }

}
