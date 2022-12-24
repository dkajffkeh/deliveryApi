package com.barogo.common.utils;

import com.barogo.common.code.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
public class HttpServletRequestUtils {

    public static final String DEFAULT_MESSAGE = "InvalidKey";

    public static ResultCode extractResultCode(HttpServletRequest request){
        return (ResultCode) request.getAttribute("resultCode");
    }

    public static String extractMessage(HttpServletRequest request, String key){
        return isEmpty(request.getAttribute(key))? DEFAULT_MESSAGE : (String)request.getAttribute(key);
    }
}
