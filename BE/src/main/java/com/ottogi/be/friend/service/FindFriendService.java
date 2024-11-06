package com.ottogi.be.friend.service;

import com.ottogi.be.dog.repository.DogRepository;
import com.ottogi.be.friend.dto.FriendDetailsDto;
import com.ottogi.be.friend.dto.response.FriendDetailsResponse;
import com.ottogi.be.friend.dto.response.FriendListResponse;
import com.ottogi.be.friend.exception.FriendRelationShipNotFoundException;
import com.ottogi.be.friend.repository.FriendRepository;
import com.ottogi.be.member.domain.Member;
import com.ottogi.be.member.exception.MemberNotFoundException;
import com.ottogi.be.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindFriendService {

    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;
    private final DogRepository dogRepository;

    @Transactional(readOnly = true)
    public List<FriendListResponse> findFriendList(String longinId) {
        Member member = memberRepository.findByLoginId(longinId)
                .orElseThrow(MemberNotFoundException::new);

        List<Long> list = friendRepository.findByMemberId(member.getId());
        List<Member> members = memberRepository.findAllById(list);

        List<FriendListResponse> friendListResponses = new ArrayList<>();

        for (Member m : members) {
            List<String> dogs = dogRepository.findDogsNameByMember(m);
            FriendListResponse friendListResponse = new FriendListResponse(
                    m.getId(),
                    m.getProfileImage(),
                    m.getNickname(),
                    dogs
            );
            friendListResponses.add(friendListResponse);
        }
        return friendListResponses;
    }

    @Transactional(readOnly = true)
    public FriendDetailsResponse findFriendDetails(FriendDetailsDto friendDetailsDto) {
        Member member = memberRepository.findByLoginId(friendDetailsDto.getLoginId())
                .orElseThrow(MemberNotFoundException::new);
        if (!friendRepository.isFriend(member.getId(), friendDetailsDto.getFriendMemberId())) throw new FriendRelationShipNotFoundException();
        Member friend = memberRepository.findById(friendDetailsDto.getFriendMemberId())
                .orElseThrow(MemberNotFoundException::new);
        LocalDate currentDate = LocalDate.now();
        int age = currentDate.getYear() - friend.getBirth().getYear();
        return new FriendDetailsResponse(friend.getProfileImage(), friend.getNickname(), friend.getGender(), age);
    }
}
