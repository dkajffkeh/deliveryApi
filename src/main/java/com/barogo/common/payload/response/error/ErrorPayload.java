package com.barogo.common.payload.response.error;

import lombok.Getter;

@Getter
public class ErrorPayload {

    private final String message;

    private final String description;

    public ErrorPayload(String message, String description) {
        this.message = message;
        this.description = description;
    }

}
