package com.ottogi.gps.walking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WalkingStartRequest {
    private List<Long> dogIds;
    private double lat;
    private double lng;


}
