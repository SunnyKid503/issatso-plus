package com.university.forum.usermanagement.Services;

import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.usermanagement.Dtos.Request.ReportRequest;

import java.util.UUID;

public interface ReportService {
    void reportPost(UUID memberId, ReportRequest reportRequest) throws ElementNotFoundException;

    void reportComment(UUID memberId, ReportRequest reportRequest) throws ElementNotFoundException;
}
