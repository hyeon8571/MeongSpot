package com.ottogi.be.walking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WalkingLogDto {
    private Long walkingLogId;
    private String date;
    private String dogImage;
    private String dogName;
    private int totalWalkingTime;
    private double distance;
}
