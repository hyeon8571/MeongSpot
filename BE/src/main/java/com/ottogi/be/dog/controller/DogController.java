package com.ottogi.be.dog.controller;

import com.ottogi.be.common.dto.response.ApiResponse;
import com.ottogi.be.dog.dto.response.PersonalityResponse;
import com.ottogi.be.dog.service.BreedService;
import com.ottogi.be.dog.service.PersonalityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dogs")
public class DogController {

    private final BreedService breedService;
    private final PersonalityService personalityService;

    @GetMapping("/breed")
    public ResponseEntity<?> breedList() {
        List<String> result = breedService.findBreedList();
        return ResponseEntity.ok(new ApiResponse<>("DO100", "견종 조회 성공", result));
    }

    @GetMapping("/breed/search")
    public ResponseEntity<?> breedSearch(@RequestParam String keyword) {
        List<String> result = breedService.findBreed(keyword);
        return ResponseEntity.ok(new ApiResponse<>("DO101", "견종 검색 성공", result));
    }

    @GetMapping("/personality")
    public ResponseEntity<?> personalityList() {
        List<PersonalityResponse> result = personalityService.findPersonalityList();
        return ResponseEntity.ok(new ApiResponse<>("DO102", "성격 조회 성공", result));
    }

}
