package com.ottogi.be.walkingLog.controller;

import com.ottogi.be.walkingLog.service.WalkingLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/walking-log")
@RequiredArgsConstructor
public class WalkingLogController {
    private final WalkingLogService walkingLogService;

}
