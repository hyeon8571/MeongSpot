package com.ottogi.be.friend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendListResponse {
    private Long id;
    private String profileImage;
    private String nickname;
    private List<String> dogNames;
}
