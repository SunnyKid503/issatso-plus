package com.university.forum.reportmanagement.Dtos.Messages;

import com.university.forum.reportmanagement.Models.Enums.TargetType;
import java.util.UUID;

public class ReportMessage {

    private UUID reportedId;
    private String targetId;
    private TargetType targetType;
    private String targetSnippet;
    private String reason;

    public UUID getReportedId() {
        return reportedId;
    }

    public void setReportedId(UUID reportedId) {
        this.reportedId = reportedId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public String getTargetSnippet() {
        return targetSnippet;
    }

    public void setTargetSnippet(String targetSnippet) {
        this.targetSnippet = targetSnippet;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ReportMessage(UUID reportedId, String targetId, TargetType targetType, String targetSnippet, String reason) {
        this.reportedId = reportedId;
        this.targetId = targetId;
        this.targetType = targetType;
        this.targetSnippet = targetSnippet;
        this.reason = reason;
    }

    public ReportMessage() {
    }

    @Override
    public String toString() {
        return "ReportMessage{" +
                "reportedId=" + reportedId +
                ", targetId='" + targetId + '\'' +
                ", targetType=" + targetType +
                ", targetSnippet='" + targetSnippet + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
