package com.ottogi.be.member.service;

import com.ottogi.be.auth.exception.PhoneVerificationNotCompletedException;
import com.ottogi.be.common.constants.RedisKeyConstants;
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
    private final CheckInfoService checkInfoService;

    @Transactional
    public void signup(SignupRequest request) {
        String issuedUuid = (String) redisService.getData(RedisKeyConstants.PHONE_AUTH_VERIFIED + request.getPhone());

        if (issuedUuid == null || !issuedUuid.equals(request.getUuid())) {
            throw new PhoneVerificationNotCompletedException();
        }

        checkInfoService.checkId(request.getLoginId());
        checkInfoService.checkNickname(request.getNickname());

        memberRepository.save(request.toEntity());
        redisService.deleteData(RedisKeyConstants.PHONE_AUTH_VERIFIED + request.getPhone());
    }
}
