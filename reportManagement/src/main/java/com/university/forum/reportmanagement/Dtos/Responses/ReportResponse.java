package com.university.forum.reportmanagement.Dtos.Responses;

import com.university.forum.reportmanagement.Models.Enums.ReportStatus;
import com.university.forum.reportmanagement.Models.Enums.TargetType;
import com.university.forum.reportmanagement.Models.Report;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

public class ReportResponse {
    private UUID id;
    private UUID reporterId;
    private String targetId;
    private TargetType targetType;
    private String targetSnippet;
    private String reason;
    private ReportStatus status;
    private UUID handledBy;
    private Timestamp handledAt;
    private Timestamp createdAt;
    private String adminFullName;

    public ReportResponse(UUID id, UUID reporterId, String targetId, TargetType targetType, String targetSnippet, String reason, ReportStatus status, UUID handledBy, Timestamp handledAt, Timestamp createdAt, String adminFullName) {
        this.id = id;
        this.reporterId = reporterId;
        this.targetId = targetId;
        this.targetType = targetType;
        this.targetSnippet = targetSnippet;
        this.reason = reason;
        this.status = status;
        this.handledBy = handledBy;
        this.handledAt = handledAt;
        this.createdAt = createdAt;
        this.adminFullName = adminFullName;
    }

    public String getAdminFullName() {
        return adminFullName;
    }

    public void setAdminFullName(String adminFullName) {
        this.adminFullName = adminFullName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getReporterId() {
        return reporterId;
    }

    public void setReporterId(UUID reporterId) {
        this.reporterId = reporterId;
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

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public UUID getHandledBy() {
        return handledBy;
    }

    public void setHandledBy(UUID handledBy) {
        this.handledBy = handledBy;
    }

    public Timestamp getHandledAt() {
        return handledAt;
    }

    public void setHandledAt(Timestamp handledAt) {
        this.handledAt = handledAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public ReportResponse() {
    }

    public static ReportResponse ReportToReportResponse(Report report){
        return new ReportResponse(
                report.getId(),
                report.getReporterId(),
                report.getTargetId(),
                report.getTargetType(),
                report.getTargetSnippet(),
                report.getReason(),
                report.getStatus(),
                report.getHandledBy(),
                report.getHandledAt(),
                report.getCreatedAt(),
                report.getAdminFullName()
        );
    }
}
