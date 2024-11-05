package com.ottogi.be.spot.controller;

import com.ottogi.be.common.dto.response.ApiResponse;
import com.ottogi.be.spot.domain.Spot;
import com.ottogi.be.spot.dto.SpotDto;
import com.ottogi.be.spot.dto.response.SpotResponse;
import com.ottogi.be.spot.service.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/meeting-spot")
@RequiredArgsConstructor
public class SpotController {

    private final SpotService spotService;

    @GetMapping
    public ResponseEntity<?> getWalkingSpots(
            @RequestParam("lat") BigDecimal lat,
            @RequestParam("lng") BigDecimal lng,
            @RequestParam("radius") int radius) {
        List<SpotResponse> result = spotService.findWalkingSpots(SpotDto.builder().lat(lat).lng(lng).radius(radius).build());
        return ResponseEntity.ok(new ApiResponse<>("MP100", "산책 위치 조회 성공", result));
    }

}
