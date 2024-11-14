package com.ottogi.be.meeting.service;

import com.ottogi.be.chat.repository.ChatRoomRepository;
import com.ottogi.be.meeting.domain.Meeting;
import com.ottogi.be.meeting.domain.MeetingMember;
import com.ottogi.be.meeting.dto.*;
import com.ottogi.be.meeting.dto.response.FindMeetingResponse;
import com.ottogi.be.meeting.dto.response.FindMyMeetingResponse;
import com.ottogi.be.meeting.dto.response.MeetingResponse;
import com.ottogi.be.meeting.dto.response.MeetingTopResponse;
import com.ottogi.be.meeting.exception.MeetingNotFoundException;
import com.ottogi.be.meeting.repository.HashTagRepository;
import com.ottogi.be.meeting.repository.MeetingMemberRepository;
import com.ottogi.be.meeting.repository.MeetingRepository;
import com.ottogi.be.member.domain.Member;
import com.ottogi.be.member.exception.MemberNotFoundException;
import com.ottogi.be.member.repository.MemberRepository;
import com.ottogi.be.spot.domain.Spot;
import com.ottogi.be.spot.exception.SpotNotFoundException;
import com.ottogi.be.spot.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindMeetingService {

    private final SpotRepository spotRepository;
    private final MeetingRepository meetingRepository;
    private final HashTagRepository hashTagRepository;
    private final MeetingMemberRepository meetingMemberRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional(readOnly = true)
    public List<MeetingResponse> findMeetingList(MeetingDto meetingDto) {
        Spot spot = spotRepository.findById(meetingDto.getSpotId())
                .orElseThrow(SpotNotFoundException::new);

        List<Meeting> meetings = new ArrayList<>();
        if (meetingDto.getOrder().equals("recent")) {
            meetings = meetingRepository.findAllBySpotAndMeetingAtAfterOrderByIdDesc(spot, LocalDateTime.now());
        } else if (meetingDto.getOrder().equals("remain")) {
            meetings = meetingRepository.findAllBySpotAndMeetingAtAfterOrderByMeetingAt(spot, LocalDateTime.now());
        }

        List<MeetingHashtagDto> hashtags = hashTagRepository.findAllByMeetings(meetings);
        Map<Long, List<String>> hashtagMap = hashtags.stream()
                .collect(Collectors.groupingBy(MeetingHashtagDto::getMeetingId, Collectors.mapping(MeetingHashtagDto::getHashtag, Collectors.toList())));

        return meetings.stream()
                .map(meeting -> MeetingResponse.builder()
                        .meetingId(meeting.getId())
                        .title(meeting.getTitle())
                        .participants(meeting.getParticipants())
                        .maxParticipants(meeting.getMaxParticipants())
                        .meetingAt(meeting.getMeetingAt())
                        .detailLocation(meeting.getDetailLocation())
                        .hashtag(hashtagMap.getOrDefault(meeting.getId(), Collections.emptyList()))
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MeetingTopResponse findMeetingTopList(Long spotId) {
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(SpotNotFoundException::new);
        List<Meeting> meetings = meetingRepository.findTop5BySpotAndMeetingAtAfterOrderByIdDesc(spot, LocalDateTime.now());

        List<MeetingHashtagDto> hashtags = hashTagRepository.findAllByMeetings(meetings);
        Map<Long, List<String>> hashtagMap = hashtags.stream()
                .collect(Collectors.groupingBy(MeetingHashtagDto::getMeetingId, Collectors.mapping(MeetingHashtagDto::getHashtag, Collectors.toList())));

        List<MeetingResponse> meetingResponses = meetings.stream()
                .map(meeting -> MeetingResponse.builder()
                        .meetingId(meeting.getId())
                        .title(meeting.getTitle())
                        .participants(meeting.getParticipants())
                        .maxParticipants(meeting.getMaxParticipants())
                        .meetingAt(meeting.getMeetingAt())
                        .detailLocation(meeting.getDetailLocation())
                        .hashtag(hashtagMap.getOrDefault(meeting.getId(), Collections.emptyList()))
                        .build()
                )
                .toList();

        return MeetingTopResponse.builder()
                .spotName(spot.getName())
                .meetings(meetingResponses)
                .build();
    }

    @Transactional(readOnly = true)
    public FindMeetingResponse findMeeting(FindMeetingDto dto) {
        Member member = memberRepository.findByLoginId(dto.getLoginId())
                .orElseThrow(MemberNotFoundException::new);
        Meeting meeting = meetingRepository.findById(dto.getMeetingId())
                .orElseThrow(MeetingNotFoundException::new);

        Boolean isParticipate = meetingMemberRepository.existsByMemberAndMeeting(member, meeting);

        return FindMeetingResponse.of(meeting, isParticipate);
    }

    @Transactional
    public boolean isParticipate(Long dogId) {
        return meetingMemberRepository.existsByDogId(dogId);
    }

    @Transactional(readOnly = true)
    public List<FindMyMeetingResponse> findMyMeetingList(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(MemberNotFoundException::new);

        List<MeetingMember> meetingMemberList = meetingMemberRepository.findAllByMemberId(member.getId());

        List<Long> meetingIds = meetingMemberList.stream()
                .map(meetingMember -> meetingMember.getMeeting().getId())
                .distinct()
                .toList();

        Map<Long, List<String>> dogNamesMap = meetingMemberList.stream()
                .collect(Collectors.groupingBy(
                        meetingMember -> meetingMember.getMeeting().getId(),
                        Collectors.mapping(meetingMember -> meetingMember.getDog().getName(), Collectors.toList())
                ));

        List<Long> chatRoomIdList = meetingRepository.findChatRoomIdByIds(meetingIds);

        List<MeetingHashtagDto> hashtags = hashTagRepository.findAllByMeetingIds(meetingIds);
        Map<Long, List<String>> hashtagMap = hashtags.stream()
                .collect(Collectors.groupingBy(MeetingHashtagDto::getMeetingId, Collectors.mapping(MeetingHashtagDto::getHashtag, Collectors.toList())));

        List<FindMyMeetingResponse> myMeetingResponses = meetingMemberList.stream()
                .map(MeetingMember::getMeeting)
                .distinct()
                .map(meeting -> FindMyMeetingResponse.builder()
                        .meetingId(meeting.getId())
                        .title(meeting.getTitle())
                        .participants(meeting.getParticipants())
                        .maxParticipants(meeting.getMaxParticipants())
                        .meetingAt(meeting.getMeetingAt())
                        .spotName(meeting.getSpot().getName())
                        .dogs(dogNamesMap.get(meeting.getId()))
                        .hashtag(hashtagMap.getOrDefault(meeting.getId(), Collections.emptyList()))
                        .chatRoomId(meeting.getChatRoom().getId())
                        .build()
                )
                .toList();

        return myMeetingResponses;
    }
}
