package com.ottogi.gps.walking.service;

import com.ottogi.gps.walking.dto.request.WalkingStartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalkingStartService {

    @Transactional
    public void startWalking(WalkingStartRequest request) {

    }
}
