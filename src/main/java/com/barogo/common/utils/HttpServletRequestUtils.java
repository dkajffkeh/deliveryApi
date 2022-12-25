package com.barogo.common.utils;

import com.barogo.common.code.ResultCode;
import com.barogo.common.exception.ApiServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
public class HttpServletRequestUtils {

    public static final String DEFAULT_MESSAGE = "InvalidKey";

    public static final String MESSAGE_KEY = "message";

    public static final String RESULT_CODE_KEY = "resultCode";

    public static ResultCode extractResultCode(HttpServletRequest request){
        return (ResultCode) request.getAttribute("resultCode");
    }

    public static String extractMessage(HttpServletRequest request, String key){
        return isEmpty(request.getAttribute(key))? DEFAULT_MESSAGE : (String)request.getAttribute(key);
    }

    public static void buildRequestErrorMsg(HttpServletRequest request, ApiServerException e) {
        request.setAttribute(MESSAGE_KEY,e.getResultCode().getResultMessage());
        request.setAttribute(RESULT_CODE_KEY,e.getResultCode());
    }
}
