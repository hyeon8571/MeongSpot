package com.ottogi.chat.dto;

import com.ottogi.chat.domain.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    private Long senderId;
    private String message;
    private String nickname;
    private String profileImage;
    private LocalDateTime sentAt;
    private MessageType messageType;
}
