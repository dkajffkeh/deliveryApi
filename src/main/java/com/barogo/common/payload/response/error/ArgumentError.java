package com.barogo.common.payload.response.error;

import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ArgumentError {

    private String field;

    private String message;

    private Object value;

    public ArgumentError(String field, String message, Object value) {
        this.field = field;
        this.message = message;
        this.value = value;
    }

    public static List<ArgumentError> argumentErrorBuilder(MethodArgumentNotValidException e) {
        final List<ArgumentError> argumentErrors = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(objectError -> {
            final ArgumentError argumentError = new ArgumentError(
                    objectError.getField(),
                    objectError.getDefaultMessage(),
                    objectError.getRejectedValue()
            );

            argumentErrors.add(argumentError);
        });

        return argumentErrors;
    }
}
