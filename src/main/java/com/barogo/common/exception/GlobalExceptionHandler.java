package com.barogo.common.exception;

import com.barogo.common.code.ResultCode;
import com.barogo.common.payload.response.error.ArgumentError;
import com.barogo.common.payload.response.ResponseData;
import com.barogo.common.payload.response.error.ErrorPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.barogo.common.payload.response.error.ArgumentError.argumentErrorBuilder;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData<ErrorPayload>> unhandledException(Exception e) {
        log.error("unhandled", e);
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ResponseData.error(new ErrorPayload("Unhandled Exception", e.getClass().getSimpleName())));
    }

    @ExceptionHandler(ApiServerException.class)
    public ResponseEntity<ResponseData<ErrorPayload>> methodArgumentNotValidException(
            ApiServerException e) {

        log.error("ApiServerException");

        final ResultCode resultCode = e.getResultCode();

        return ResponseEntity
                .status(resultCode.getHttpStatus())
                .body(ResponseData.fail(new ErrorPayload(
                        resultCode.getResultMessage(),
                        e.getDescription())));
    }


    @ExceptionHandler(UserIdAlreadyExistException.class)
    public ResponseEntity<ResponseData<ErrorPayload>> methodArgumentNotValidException(
            UserIdAlreadyExistException e) {

        log.error("userIdAlreadyExistException");

        final ResultCode resultCode = e.getResultCode();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ResponseData.fail(new ErrorPayload(
                        resultCode.getResultMessage(),
                        e.getDescription())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData<List<ArgumentError>>> methodArgumentNotValidException(
            MethodArgumentNotValidException e) {

        log.error("methodArgumentNotValidException");

        return ResponseEntity
                .badRequest()
                .body(ResponseData.fail(argumentErrorBuilder(e)));
    }
}
