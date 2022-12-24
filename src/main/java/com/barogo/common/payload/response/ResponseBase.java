package com.barogo.common.payload.response;

import com.barogo.common.code.ResultCode;
import com.barogo.common.utils.DateTimeUtils;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.barogo.common.utils.DateTimeUtils.convertLocalDateTimeToString;

@Getter
public class ResponseBase {

    private final ResultCode resultCode;

    private final String systemDt = convertLocalDateTimeToString(LocalDateTime.now());

    public ResponseBase(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultCode() {return resultCode.getResultCode();}
}
