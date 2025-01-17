package com.ottogi.be.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ExceptionConstants constants;

    public CustomException(ExceptionConstants constants) {
        this.constants = constants;
    }
}
