package com.ottogi.gps.walking.dto.request;

import com.ottogi.gps.walking.dto.WalkingStartDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WalkingStartRequest {
    private List<Long> dogIds;

    public WalkingStartDto toDto(String loginId){
        return WalkingStartDto.builder()
                .dogIds(this.dogIds)
                .build();
    }

}
