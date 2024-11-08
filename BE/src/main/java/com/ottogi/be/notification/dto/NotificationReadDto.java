package com.ottogi.be.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationReadDto {
    private Long notificationId;
    private String loginId;

}
