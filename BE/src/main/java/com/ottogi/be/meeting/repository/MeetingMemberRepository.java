package com.ottogi.be.meeting.repository;

import com.ottogi.be.meeting.domain.Meeting;
import com.ottogi.be.meeting.domain.MeetingMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingMemberRepository extends JpaRepository<MeetingMember, Long> {

    @Query("""
        SELECT DISTINCT cm.meeting
        FROM MeetingMember cm
        JOIN FETCH cm.meeting
        WHERE cm.member.id = :memberId
    """)
    List<Meeting> findMeetingsByMemberId(@Param("memberId") Long memberId);
}