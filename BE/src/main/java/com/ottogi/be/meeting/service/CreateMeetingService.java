package com.ottogi.be.meeting.service;

import com.ottogi.be.chat.domain.ChatRoom;
import com.ottogi.be.chat.domain.enums.ChatRoomType;
import com.ottogi.be.chat.repository.ChatRoomRepository;
import com.ottogi.be.dog.domain.Dog;
import com.ottogi.be.dog.exception.DogOwnerMismatchException;
import com.ottogi.be.dog.repository.DogRepository;
import com.ottogi.be.meeting.domain.Hashtag;
import com.ottogi.be.meeting.domain.Meeting;
import com.ottogi.be.meeting.domain.MeetingMember;
import com.ottogi.be.meeting.dto.CreateMeetingDto;
import com.ottogi.be.meeting.exception.CannotCreateMeetingException;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateMeetingService {

    private final SpotRepository spotRepository;
    private final MemberRepository memberRepository;
    private final DogRepository dogRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MeetingRepository meetingRepository;
    private final HashTagRepository hashTagRepository;
    private final MeetingMemberRepository meetingMemberRepository;

    @Transactional
    public void addMeeting(CreateMeetingDto dto) {
        Spot meetingSpot = spotRepository.findById(dto.getSpotId())
                .orElseThrow(SpotNotFoundException::new);
        Member member = memberRepository.findByLoginId(dto.getLoginId())
                .orElseThrow(MemberNotFoundException::new);

        String title = dto.getTitle();
        if (title == null || title.isEmpty()) throw new CannotCreateMeetingException();

        LocalDate date = dto.getDate();
        LocalDateTime meetingStartTime = date.atTime(dto.getHour(), dto.getMin());
        LocalDateTime currentTime = LocalDateTime.now();
        if (meetingStartTime.isBefore(currentTime)) throw new CannotCreateMeetingException();

        int maxParticipants = dto.getMaxParticipants();
        if (maxParticipants <= 1) throw new CannotCreateMeetingException();

        List<Long> dogIds = dto.getDogIds();
        if (dogIds == null || dogIds.isEmpty()) throw new CannotCreateMeetingException();
        if (!dogRepository.isOwner(member, dogIds)) throw new DogOwnerMismatchException();

        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomType(ChatRoomType.MEETING)
                .build();
        ChatRoom chat = chatRoomRepository.save(chatRoom);

        Meeting meeting = dto.toEntity(meetingSpot, chat, meetingStartTime);
        meetingRepository.save(meeting);

        List<String> hashtag = dto.getHashtag();
        if (hashtag != null && !hashtag.isEmpty()) {
            List<Hashtag> tags = new ArrayList<>();
            for (String hashtagItem : hashtag) {
                Hashtag tag= Hashtag.builder()
                        .meeting(meeting)
                        .tag(hashtagItem)
                        .build();
                tags.add(tag);
            }
            hashTagRepository.saveAll(tags);
        }

        List<Dog> dogs = dogRepository.findAllById(dogIds);
        List<MeetingMember> meetingMemberList = new ArrayList<>();
        for (Dog dog : dogs) {
            MeetingMember meetingMember = MeetingMember.builder()
                    .meeting(meeting)
                    .member(member)
                    .dog(dog)
                    .build();
            meetingMemberList.add(meetingMember);
        }
        meetingMemberRepository.saveAll(meetingMemberList);

    }
}
