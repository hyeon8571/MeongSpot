package com.ottogi.be.meeting.repository;

import com.ottogi.be.meeting.domain.MeetingMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeetingMemberRepository extends JpaRepository<MeetingMember, Long> {

    @Query("""
            SELECT d.profileImage
            FROM MeetingMember mm
            JOIN Dog d ON d.id = mm.dog.id
            WHERE mm.meeting.id = :meetingId
            """)
    List<String> findDogImageByMeetingId(Long meetingId);
}
