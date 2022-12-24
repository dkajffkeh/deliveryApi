package com.barogo.domain.auth.controller;

import com.barogo.domain.auth.controller.parameter.signup.SignUpParameter;
import com.barogo.domain.auth.controller.payload.LoginPayload;
import com.barogo.domain.auth.controller.payload.SignUpSuccessPayload;
import com.barogo.common.payload.response.ResponseData;
import com.barogo.domain.auth.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.barogo.common.utils.HttpServletRequestUtils.extractResultCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseData<SignUpSuccessPayload> signUp(
            @RequestBody @Validated SignUpParameter loginParameter) {
        return ResponseData.success(userService.signUp(loginParameter));
    }

    @PostMapping("/token")
    public ResponseData<LoginPayload> successLogin(HttpServletRequest request, HttpServletResponse response) {
        return ResponseData.success(userService.loginSuccessHandler(request,response));
    }

    @PostMapping("/login/fail")
    public ResponseData<LoginPayload> loginFailHandler(HttpServletRequest request){
        return ResponseData.success(extractResultCode(request),userService.loginFailHandler(request));
    }
}

