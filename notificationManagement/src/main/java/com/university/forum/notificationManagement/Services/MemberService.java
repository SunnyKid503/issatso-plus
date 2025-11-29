package com.university.forum.notificationManagement.Services;

import com.university.forum.notificationManagement.Dtos.Messages.MemberMessage;
import com.university.forum.notificationManagement.Dtos.Messages.StudentMessage;
import com.university.forum.notificationManagement.Models.Member;

public interface MemberService {

    void createMember(MemberMessage memberMessage);
}
