package com.ottogi.be.meeting.domain;

import jakarta.persistence.*;

@Entity
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Column(length = 8, nullable = false)
    private String tag;
}
