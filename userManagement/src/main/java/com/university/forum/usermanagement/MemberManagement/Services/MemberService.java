package com.university.forum.usermanagement.MemberManagement.Services;

import com.university.forum.usermanagement.MemberManagement.Dtos.Request.ReportRequest;
import com.university.forum.usermanagement.MemberManagement.Dtos.Response.MemberResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface MemberService {
    MemberResponseDto getMemberMe(UUID memberId);

    MemberResponseDto getMemberById(UUID memberId);

    String updateProfileImage(UUID memberId, MultipartFile image);

    void reportMember(UUID memberId, ReportRequest reportRequest);
}
