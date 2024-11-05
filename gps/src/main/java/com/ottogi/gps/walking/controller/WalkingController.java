package com.ottogi.gps.walking.controller;

import com.ottogi.gps.auth.dto.LoginMemberInfo;
import com.ottogi.gps.common.dto.response.ApiResponse;
import com.ottogi.gps.walking.dto.request.WalkingStartRequest;
import com.ottogi.gps.walking.service.WalkingStartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/walking")
public class WalkingController {

    private WalkingStartService walkingService;

    @PostMapping("/start")
    public ResponseEntity<?> walkingStart(@AuthenticationPrincipal LoginMemberInfo loginMemberInfo,
                                          @RequestBody WalkingStartRequest request){
        walkingService.startWalking(request);
        return ResponseEntity.ok(new ApiResponse<>("WK100", "산책 시작 성공",null));
    }


}
