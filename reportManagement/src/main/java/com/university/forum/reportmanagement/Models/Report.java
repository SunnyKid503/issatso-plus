package com.university.forum.reportmanagement.Models;

import com.university.forum.reportmanagement.Models.Enums.ReportStatus;
import com.university.forum.reportmanagement.Models.Enums.TargetType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID reporterId;
    @Column(nullable = false)
    private String targetId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TargetType targetType;
    private String targetSnippet;
    private String reason;

    @Enumerated(EnumType.STRING)
    private ReportStatus status=ReportStatus.PENDING;

    private UUID handledBy;
    private Timestamp handledAt;
    private String adminFullName;

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;


    public Report(UUID id, UUID reporterId, String targetId, TargetType targetType, String targetSnippet, String reason, ReportStatus status, UUID handledBy, Timestamp handledAt, String adminFullName, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.reporterId = reporterId;
        this.targetId = targetId;
        this.targetType = targetType;
        this.targetSnippet = targetSnippet;
        this.reason = reason;
        this.status = status;
        this.handledBy = handledBy;
        this.handledAt = handledAt;
        this.adminFullName = adminFullName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public void setReporterId(UUID reportedId) {
        this.reporterId = reportedId;
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

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Report() {
    }
}