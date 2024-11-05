package com.ottogi.gps.walking.service;

import com.ottogi.gps.member.domain.Member;
import com.ottogi.gps.member.repository.MemberRepository;
import com.ottogi.gps.walking.dto.WalkingStartDto;
import com.ottogi.gps.member.exception.MemberNotFoundException;
import com.ottogi.gps.walking.repository.WalkingRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class WalkingStartService {
    private final MemberRepository memberRepository;
    private final WalkingRedisRepository walkingRedisRepository;

    @Transactional
    public void startWalking(WalkingStartDto dto) {
        Member member = memberRepository.findByLoginId(dto.getLoginId()).orElseThrow(MemberNotFoundException::new);

        Long startTime = Instant.now().getEpochSecond();

        walkingRedisRepository.saveStartTime(member.getId(), startTime);
        walkingRedisRepository.saveDogIds(member.getId(), dto.getDogIds());


    }





}
