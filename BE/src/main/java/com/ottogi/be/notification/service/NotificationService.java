package com.ottogi.be.notification.service;

import com.ottogi.be.member.domain.Member;
import com.ottogi.be.member.repository.MemberRepository;
import com.ottogi.be.notification.domain.Notification;
import com.ottogi.be.notification.dto.NotificationReadDto;
import com.ottogi.be.notification.dto.response.NotificationResponse;
import com.ottogi.be.notification.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ottogi.be.member.exception.MemberNotFoundException;
import com.ottogi.be.notification.exception.NotificationNotFoundException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public List<NotificationResponse> findNotificationList(String loginId){
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(MemberNotFoundException::new);
          return notificationRepository.findAllByReceiver(member);
    }

    @Transactional
    public void readNotification(NotificationReadDto request){
        Notification notification = notificationRepository.findById(request.getNotificationId())
                .orElseThrow(NotificationNotFoundException::new);

        Member member = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(MemberNotFoundException::new);

        isMemberNotification(notification,member);

        notification.read();
        notificationRepository.save(notification);

    }

    @Transactional
    public void isMemberNotification(Notification notification, Member member) {
        if(!notification.getReceiver().getId().equals(member.getId()))
            throw new NotificationNotFoundException();
    }


}
