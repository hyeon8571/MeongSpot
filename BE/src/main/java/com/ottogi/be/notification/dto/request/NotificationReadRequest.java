package com.ottogi.be.notification.dto.request;

import com.ottogi.be.notification.dto.NotificationReadDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationReadRequest {
    private Long notificationId;

    public NotificationReadDto toDto(String loginId){
        return NotificationReadDto.builder()
                .notificationId(this.notificationId)
                .loginId(loginId)
                .build();
    }

}
