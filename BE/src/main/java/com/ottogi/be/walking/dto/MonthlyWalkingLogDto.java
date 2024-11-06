package com.ottogi.be.walking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyWalkingLogDto {
    private String dogImage;
    private String dogName;
    private int monthlyWalkCount;
    private int monthlyWalkTime;
    private double monthlyWalkDistance;
}
