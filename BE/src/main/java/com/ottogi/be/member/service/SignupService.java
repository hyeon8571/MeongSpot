package com.ottogi.be.member.service;

import com.ottogi.be.common.infra.RedisService;
import com.ottogi.be.member.dto.request.SignupRequest;
import com.ottogi.be.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final MemberRepository memberRepository;
    private final RedisService redisService;

    @Transactional
    public void signup(SignupRequest request) {

    }
}
