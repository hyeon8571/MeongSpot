package com.ottogi.be.meeting.repository;

import com.ottogi.be.dog.domain.Dog;
import com.ottogi.be.dog.dto.response.FindDogProfileImage;
import com.ottogi.be.meeting.domain.Meeting;
import com.ottogi.be.meeting.domain.MeetingMember;
import com.ottogi.be.member.domain.Member;
import com.ottogi.be.member.dto.response.FindMeetingMemberResponse;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeetingMemberRepository extends JpaRepository<MeetingMember, Long> {

    @Query("""
            SELECT NEW com.ottogi.be.dog.dto.response.FindDogProfileImage(d.id, d.profileImage)
            FROM MeetingMember mm
            JOIN Dog d ON d.id = mm.dog.id
            WHERE mm.meeting.id = :meetingId
            """)
    List<FindDogProfileImage> findDogImageByMeetingId(Long meetingId);

    @Query("""
            SELECT mm.dog
            FROM MeetingMember mm
            WHERE mm.meeting.id = :meetingId AND mm.member.id = :memberId
            """)
    List<Dog> findDogsFromMeetingIdAndMemberId(Long meetingId, Long memberId);

    boolean existsByMemberAndMeeting(Member member, Meeting meeting);

    @Query("""
            SELECT DISTINCT NEW com.ottogi.be.member.dto.response.FindMeetingMemberResponse(
            mm.member.id, mm.member.profileImage, mm.member.nickname, mm.member.birth, mm.member.gender
            ) FROM MeetingMember mm
            WHERE mm.meeting.id = :meetingId
            """)
    List<FindMeetingMemberResponse> findMemberByMeetingId(Long meetingId);

    void deleteAllByMemberAndMeeting(Member member, Meeting meeting);

    @Query("""
            SELECT DISTINCT m.member 
            FROM MeetingMember m 
            WHERE m.meeting = :meeting AND m.member != :member
            """)
    List<Member> findMembersForNotification(@Param("meeting") Meeting meeting, @Param("member") Member member);

    boolean existsByDogId(Long dogId);

    @Query(value = "SELECT mm FROM MeetingMember mm JOIN FETCH mm.dog JOIN FETCH mm.meeting JOIN FETCH mm.meeting.spot WHERE mm.member.id = :memberId ORDER BY mm.meeting.meetingAt ASC")
    List<MeetingMember> findAllByMemberId(Long memberId);

}
