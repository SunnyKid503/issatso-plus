package com.university.forum.usermanagement.MemberManagement.Dtos.Request;


import com.university.forum.usermanagement.MemberManagement.Models.Enums.TargetType;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class ReportRequest {

    @NotBlank(message = "Target id is needed")
    private UUID targetId;
    private final TargetType targetType=TargetType.USER;
    private String reason;

    public @NotBlank(message = "Target id is needed") UUID getTargetId() {
        return targetId;
    }

    public void setTargetId(@NotBlank(message = "Target id is needed") UUID targetId) {
        this.targetId = targetId;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ReportRequest(UUID targetId, String reason) {
        this.targetId = targetId;
        this.reason = reason;
    }

    public ReportRequest() {
    }

    @Override
    public String toString() {
        return "ReportRequest{" +
                "targetId=" + targetId +
                ", targetType=" + targetType +
                ", reason='" + reason + '\'' +
                '}';
    }
}
