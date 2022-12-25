package com.barogo.domain.auth.controller;

import com.barogo.common.payload.response.ResponseData;
import com.barogo.domain.auth.controller.payload.AuthFailPayload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.barogo.common.utils.HttpServletRequestUtils.extractMessage;
import static com.barogo.common.utils.HttpServletRequestUtils.extractResultCode;

@RequestMapping("/auth")
@RestController
public class AuthFailRedirectController {

    @PostMapping("/fail")
    public ResponseData<AuthFailPayload> authFailHandler(HttpServletRequest request) {
        return ResponseData.success(extractResultCode(request),
                new AuthFailPayload(extractMessage(request,"message")));
    }
}
