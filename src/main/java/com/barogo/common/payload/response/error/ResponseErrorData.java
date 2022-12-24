package com.barogo.common.payload.response.error;

import com.barogo.common.code.ResultCode;
import com.barogo.common.payload.response.ResponseData;
import com.barogo.common.payload.response.error.ErrorPayload;

public class ResponseErrorData extends ResponseData<ErrorPayload> {

    public ResponseErrorData(ResultCode resultCode, ErrorPayload data) {
        super(resultCode, data);
    }

}
