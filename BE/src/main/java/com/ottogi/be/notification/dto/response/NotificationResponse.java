package com.ottogi.be.notification.dto.response;

import com.ottogi.be.notification.domain.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class NotificationResponse {
    private Long notificationId;
    private Type type;
    private String profileImage;
    private String content;
    private LocalDate createdAt;
    private Boolean isRead;
}
