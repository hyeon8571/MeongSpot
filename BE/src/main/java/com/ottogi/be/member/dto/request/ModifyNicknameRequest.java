package com.ottogi.be.member.dto.request;

import com.ottogi.be.member.dto.ModifyNicknameDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyNicknameRequest {
    private String nickname;

    public ModifyNicknameDto toDto(String loginId) {
        return ModifyNicknameDto.builder()
                .nickname(nickname)
                .loginId(loginId)
                .build();
    }
}
