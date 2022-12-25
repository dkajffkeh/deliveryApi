package com.barogo.common.payload.response;

import com.barogo.common.code.ResultCode;
import com.barogo.common.payload.response.error.ErrorPayload;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import static com.barogo.common.code.ResultCode.*;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> extends ResponseBase {

    public ResponseData(ResultCode code) {
        this(code, null);
    }

    public ResponseData(ResultCode code, T resultData) {
        super(code.getResultCode());
        this.resultData = resultData;
        this.resultMessage = code.getResultMessage();
    }

    private final String resultMessage;
    private final T resultData;

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<T>(RESULT_0000, data);
    }

    public static <T> ResponseData<T> success(ResultCode resultCode,T data) {
        return new ResponseData<>(resultCode, data);
    }

    public static <T> ResponseData<T> ok() {
        return new ResponseData<>(RESULT_0000,null);
    }

    public static <T> ResponseData<T> error(ErrorPayload errorPayload) {
        return new ResponseData<>(RESULT_9999,null);
    }

    public static <T> ResponseData<T> fail(T errorData) {
        return new ResponseData<T>(RESULT_4000,errorData);
    }

    public static ResponseData<ErrorPayload> fail(ErrorPayload payload) {
        return new ResponseData<>(RESULT_9999, payload);
    }
}

