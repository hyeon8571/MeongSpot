package com.ottogi.be.dog.service;

import com.ottogi.be.dog.repository.DogRepository;
import com.ottogi.be.member.domain.Member;
import com.ottogi.be.member.exception.MemberNotFoundException;
import com.ottogi.be.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindDogListService {

    private final DogRepository dogRepository;
    private final MemberRepository memberRepository;

    public List<String> findDogNameList(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(MemberNotFoundException::new);
        return dogRepository.findDogsNameByMember(member);
    }
}
