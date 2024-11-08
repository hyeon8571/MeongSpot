package com.ottogi.be.notification.controller;

import com.ottogi.be.auth.dto.LoginMemberInfo;
import com.ottogi.be.common.dto.response.ApiResponse;
import com.ottogi.be.notification.dto.request.FCMTokenRequest;
import com.ottogi.be.notification.dto.response.NotificationResponse;
import com.ottogi.be.notification.service.FCMTokenService;
import com.ottogi.be.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final FCMTokenService fcmTokenService;
    private final NotificationService notificationService;

    @PostMapping("/fcm")
    public ResponseEntity<?> fcmTokenSave(@RequestBody FCMTokenRequest fcmTokenRequest,
                                          @AuthenticationPrincipal LoginMemberInfo loginMemberInfo){
        fcmTokenService.saveFcmToken(fcmTokenRequest.toDto(loginMemberInfo.getLoginId()));
        return ResponseEntity.ok(new ApiResponse<>("NO106", "FCMToken 저장 성공",null));

    }

    @GetMapping
    public ResponseEntity<?> notificationListFind(@AuthenticationPrincipal LoginMemberInfo loginMemberInfo) {
        List<NotificationResponse> notifications = notificationService.findNotificationList(loginMemberInfo.getLoginId());
        return ResponseEntity.ok(new ApiResponse<>("NO103", "알림 목록 조회 성공", notifications));
    }

}
