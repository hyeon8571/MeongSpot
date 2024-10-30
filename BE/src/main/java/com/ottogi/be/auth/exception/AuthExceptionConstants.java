package com.ottogi.be.auth.exception;

import com.ottogi.be.common.exception.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthExceptionConstants implements ExceptionConstants {

    PHONE_VERIFICATION_NOT_COMPLETED("AU001", "전화번호 인증 미실시", HttpStatus.BAD_REQUEST),
    PHONE_AUTH_CODE_MISMATCH("AU002", "전화번호 인증 실패", HttpStatus.BAD_REQUEST);

    final String code;
    final String message;
    final HttpStatus httpStatus;
}