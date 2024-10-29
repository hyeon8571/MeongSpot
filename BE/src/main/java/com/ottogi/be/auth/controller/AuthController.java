package com.ottogi.be.auth.controller;

import com.ottogi.be.auth.dto.request.SendPhoneAuthCodeRequest;
import com.ottogi.be.auth.dto.request.VerifyPhoneAuthCodeRequest;
import com.ottogi.be.auth.service.AuthPhoneService;
import com.ottogi.be.common.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthPhoneService authPhoneService;

    @PostMapping("/phone/send-auth-code")
    public ResponseEntity<?> authCodeSend(@Valid @RequestBody SendPhoneAuthCodeRequest request) {
        authPhoneService.sendAuthCode(request);
        return ResponseEntity.ok(new ApiResponse<>("AU101", "전화번호 인증 코드 전송 성공", null));
    }

    @PostMapping("/phone/verify-auth-code")
    public ResponseEntity<?> authCodeVerify(@RequestBody VerifyPhoneAuthCodeRequest request) {
        authPhoneService.verifyAuthCode(request);
        return ResponseEntity.ok(new ApiResponse<>("AU102", "전화번호 인증 성공", null));
    }
}
