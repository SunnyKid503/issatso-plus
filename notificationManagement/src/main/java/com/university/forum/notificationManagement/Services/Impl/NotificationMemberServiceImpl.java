package com.university.forum.notificationManagement.Services.Impl;

import com.university.forum.notificationManagement.Repositories.NotificationMemberRepository;
import com.university.forum.notificationManagement.Services.NotificationMemberService;
import org.springframework.stereotype.Service;

@Service
public class NotificationMemberServiceImpl implements NotificationMemberService {

    private final NotificationMemberRepository notificationMemberRepository;

    public NotificationMemberServiceImpl(NotificationMemberRepository notificationMemberRepository) {
        this.notificationMemberRepository = notificationMemberRepository;
    }
}
