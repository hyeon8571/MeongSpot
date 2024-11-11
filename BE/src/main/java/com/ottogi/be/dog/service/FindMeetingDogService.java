package com.ottogi.be.dog.service;

import com.ottogi.be.meeting.repository.MeetingMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindMeetingDogService {

    private final MeetingMemberRepository meetingMemberRepository;

    @Transactional(readOnly = true)
    public List<String> findMeetingDogImageList(Long meetingId) {
        return meetingMemberRepository.findDogImageByMeetingId(meetingId);
    }
}
