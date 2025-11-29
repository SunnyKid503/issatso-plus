package com.university.forum.usermanagement.MemberManagement.Services;

import com.university.forum.usermanagement.MemberManagement.Dtos.Request.BanRequest;
import com.university.forum.usermanagement.MemberManagement.Dtos.Response.BanResponse;

import java.util.List;
import java.util.UUID;

public interface BanService {
    List<BanResponse> getBans();

    void addBan(BanRequest banRequest);

    void updateBan(int banId, BanRequest banRequest);

    BanResponse getBanById(int banId);

    List<BanResponse> getBansByMemberId(UUID userId);

    List<BanResponse> getBansByAdminId(UUID adminId);
}
