package com.university.forum.usermanagement.Dtos.Request;


import com.university.forum.usermanagement.Models.Enum.TargetType;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class ReportRequest {

    @NotBlank(message = "Target id is needed")
    private int targetId;
    private TargetType targetType;
    private String reason;

    public TargetType getTargetType() {
        return targetType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @NotBlank(message = "Target id is needed")
    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(@NotBlank(message = "Target id is needed") int targetId) {
        this.targetId = targetId;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public ReportRequest(int targetId, TargetType targetType, String reason) {
        this.targetId = targetId;
        this.targetType = targetType;
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
