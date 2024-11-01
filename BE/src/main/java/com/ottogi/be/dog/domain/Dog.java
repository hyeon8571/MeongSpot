package com.ottogi.be.dog.domain;

import com.ottogi.be.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Member member;

    @Column(length = 16, nullable = false)
    private String name;

    private LocalDate birth;

    @Column(length = 128)
    private String introduction;

    @Column(length = 6, nullable = false)
    private String gender;

    @Column(nullable = false)
    private Boolean isNeuter;

    @Column(length = 256, nullable = false)
    private String profileImage;

    @Column(length = 6, nullable = false)
    private String size;

    @Column(nullable = false)
    private int age;

    @Column(length = 32, nullable = false)
    private String breed;
}
