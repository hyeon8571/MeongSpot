package com.ottogi.chat.controller;

import com.ottogi.chat.dto.ChatMessageDto;
import com.ottogi.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequiredArgsConstructor
public class ChatController {

    private final RabbitTemplate rabbitTemplate;
    private final ChatMessageRepository chatMessageRepository;

    private static final String CHAT_EXCHANGE_NAME = "chat.exchange";
    private static final String CHAT_QUEUE_NAME = "chat.queue";

    @MessageMapping("chat.enter.{chatRoomId}")
    public void enterChatRoom(@DestinationVariable Long chatRoomId) {

    }

    @MessageMapping("send.message.{chatRoomId}")
    public void messageSend(@Payload ChatMessageDto dto, @DestinationVariable Long chatRoomId) {
        dto.setChatRoomId(chatRoomId);
        dto.setSentAt(LocalDateTime.now());
        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, dto);
    }

    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receiveMessage(ChatMessageDto dto) {
        chatMessageRepository.save(dto.toEntity());
    }
}