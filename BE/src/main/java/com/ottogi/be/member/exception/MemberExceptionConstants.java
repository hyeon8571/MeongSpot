package com.ottogi.be.member.exception;

import com.ottogi.be.common.exception.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberExceptionConstants implements ExceptionConstants {

    LOGIN_ID_DUPLICATE("ME000", "로그인 아이디 중복 검사 실패", HttpStatus.CONFLICT),
    NICKNAME_DUPLICATE("ME001", "닉네임 중복 검사 실패", HttpStatus.CONFLICT),
    PHONE_DUPLICATE("ME002", "전화번호 중복 검사 실패", HttpStatus.CONFLICT);

    final String code;
    final String message;
    final HttpStatus httpStatus;
}
