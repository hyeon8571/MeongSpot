package com.ottogi.be.meeting.domain;

import com.ottogi.be.dog.domain.Dog;
import com.ottogi.be.member.domain.Member;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class MeetingMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id")
    private Dog dog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime joinAt;

    @Column(nullable = false)
    private Boolean isAlarm;

    @Column(nullable = false)
    private Boolean isLeave;
}
