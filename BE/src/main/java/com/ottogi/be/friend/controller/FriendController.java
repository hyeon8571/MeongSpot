package com.ottogi.be.friend.controller;

import com.ottogi.be.auth.dto.LoginMemberInfo;
import com.ottogi.be.common.dto.response.ApiResponse;
import com.ottogi.be.friend.dto.FriendDetailsDto;
import com.ottogi.be.friend.dto.response.FriendDetailsResponse;
import com.ottogi.be.friend.dto.response.FriendListResponse;
import com.ottogi.be.friend.service.FindFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final FindFriendService findFriendService;

    @GetMapping
    public ResponseEntity<?> friendList(@AuthenticationPrincipal LoginMemberInfo loginMemberInfo) {
        List<FriendListResponse> result = findFriendService.findFriendList(loginMemberInfo.getLoginId());
        return ResponseEntity.ok(new ApiResponse<>("FR100", "친구 목록 조회 성공", result));
    }

    @GetMapping("/{friendMemberId}")
    public ResponseEntity<?> friendDetails(@PathVariable Long friendMemberId,
                                           @AuthenticationPrincipal LoginMemberInfo loginMemberInfo) {
        FriendDetailsResponse result = findFriendService.findFriendDetails(new FriendDetailsDto(friendMemberId, loginMemberInfo.getLoginId()));
        return ResponseEntity.ok(new ApiResponse<>("FR101", "친구 정보 상세 조회 성공", result));
    }

}
