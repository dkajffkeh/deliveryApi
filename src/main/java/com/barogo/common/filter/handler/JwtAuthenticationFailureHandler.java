package com.barogo.common.filter.handler;

import com.barogo.common.exception.ApiServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.barogo.common.code.ResultCode.RESULT_1006;
import static com.barogo.common.code.ResultCode.RESULT_1007;


@Slf4j
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if(exception instanceof UsernameNotFoundException){
            request.setAttribute("resultCode", RESULT_1006);
            request.setAttribute("message",RESULT_1006.getResultMessage());
        }

        if(exception instanceof BadCredentialsException) {
            request.setAttribute("resultCode", RESULT_1007);
            request.setAttribute("message",RESULT_1007.getResultMessage());
        }

        request.getRequestDispatcher("/users/login/fail").forward(request,response);
    }
}
