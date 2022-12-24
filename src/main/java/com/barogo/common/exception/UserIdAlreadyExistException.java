package com.barogo.common.exception;

import com.barogo.common.code.ResultCode;
import lombok.Getter;

@Getter
public class UserIdAlreadyExistException extends RuntimeException {

    private final ResultCode resultCode;
    private final String description;

    public UserIdAlreadyExistException(ResultCode resultCode) {
        this(resultCode, resultCode.getResultMessage());
    }

    public UserIdAlreadyExistException(ResultCode resultCode, String message){
        super(message);
        this.resultCode = resultCode;
        this.description = message;
    }
}
