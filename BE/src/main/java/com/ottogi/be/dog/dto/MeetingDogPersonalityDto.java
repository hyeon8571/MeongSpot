package com.ottogi.be.dog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingDogPersonalityDto {
    private Long dogId;
    private String personality;
}
