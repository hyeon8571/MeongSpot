package com.ottogi.be.auth.dto.request;

import com.ottogi.be.member.validation.annotation.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyPhoneAuthCodeRequest {
    private String phone;
    private String authCode;
}
