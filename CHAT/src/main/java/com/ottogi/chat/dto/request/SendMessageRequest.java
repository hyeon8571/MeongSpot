package com.ottogi.chat.dto.request;

import com.ottogi.chat.domain.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {
    private String message;
    private Long senderId;
    private MessageType messageType;
}
