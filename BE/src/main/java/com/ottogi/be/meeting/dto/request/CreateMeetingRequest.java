package com.ottogi.be.meeting.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMeetingRequest {
    private String title;
    private LocalDate date;
    private int hour;
    private int min;
    private String detailLocation;
    private List<String> hashtag;
    private int maxParticipants;
    private List<Long> dogs;
}
