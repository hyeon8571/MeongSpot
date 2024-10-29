package com.ottogi.be.member.controller;

import com.ottogi.be.common.dto.response.ApiResponse;
import com.ottogi.be.common.service.RedisService;
import com.ottogi.be.member.service.MemberService;
import com.ottogi.be.member.validation.annotation.LoginId;
import com.ottogi.be.member.validation.annotation.Nickname;
import com.ottogi.be.member.validation.annotation.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/check-id")
    public ResponseEntity<?> idCheck(@LoginId @RequestParam("loginId") String loginId) {
        memberService.checkId(loginId);
        return ResponseEntity.ok(new ApiResponse<>("ME101", "로그인 아이디 중복 검사 성공", null));
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<?> nicknameCheck(@Nickname @RequestParam("nickname") String nickname) {
        memberService.checkNickname(nickname);
        return ResponseEntity.ok(new ApiResponse<>("ME102", "닉네임 중복 검사 성공", null));
    }

    @GetMapping("/check-phone")
    public ResponseEntity<?> phoneCheck(@Phone @RequestParam("phone") String phone) {
        memberService.checkPhone(phone);
        return ResponseEntity.ok(new ApiResponse<>("ME103", "전화번호 중복 검사 성공", null));
    }
}
