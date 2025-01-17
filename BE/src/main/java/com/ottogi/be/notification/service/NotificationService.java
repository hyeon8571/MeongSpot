package com.ottogi.be.notification.service;

import com.ottogi.be.member.domain.Member;
import com.ottogi.be.member.repository.MemberRepository;
import com.ottogi.be.notification.domain.Notification;
import com.ottogi.be.notification.domain.NotificationFriendInvite;
import com.ottogi.be.notification.domain.enums.Status;
import com.ottogi.be.notification.dto.NotificationDto;
import com.ottogi.be.notification.dto.response.NotificationResponse;
import com.ottogi.be.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.ottogi.be.member.exception.MemberNotFoundException;
import com.ottogi.be.notification.exception.NotificationNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<NotificationResponse> findNotificationList(String loginId){
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(MemberNotFoundException::new);
          return notificationRepository.findAllByReceiver(member);
    }

    @Transactional
    public void readNotification(NotificationDto request){
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

    @Transactional
    public void removeNotification(NotificationDto dto){
        Notification notification = notificationRepository.findById(dto.getNotificationId())
                .orElseThrow(NotificationNotFoundException::new);

        Member member = memberRepository.findByLoginId(dto.getLoginId())
                .orElseThrow(MemberNotFoundException::new);

        isMemberNotification(notification,member);

        if (notification instanceof NotificationFriendInvite notificationFriendInvite) {
            if (notificationFriendInvite.getStatus() == Status.WAIT) {
                notificationFriendInvite.reject();
            }
        }
        notification.delete();
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public Boolean checkUnreadNotification(String loginId){
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(MemberNotFoundException::new);
        return notificationRepository.existsByReceiverAndIsReadFalse(member);
    }
}
