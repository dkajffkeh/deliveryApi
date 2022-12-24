package com.barogo.common.exception;

import com.barogo.common.code.ResultCode;
import lombok.Getter;

@Getter
public class ApiServerException extends RuntimeException{

    private final ResultCode resultCode;
    private final String description;

    public ApiServerException(ResultCode resultCode) {
        this(resultCode, resultCode.getResultMessage());
    }

    public ApiServerException(ResultCode resultCode, String message){
        super(message);
        this.resultCode = resultCode;
        this.description = message;
    }

}
