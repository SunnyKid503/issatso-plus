package com.university.forum.usermanagement.MemberManagement.Dtos.Response;

import java.time.LocalDateTime;
import java.util.UUID;

public class BanResponse {

    private Long id;
    private UUID bannedUserId;
    private String reason;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UUID bannedById;
    private String bannedByFullName;
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public BanResponse(Long id, UUID bannedUserId, String reason, LocalDateTime startDate, LocalDateTime endDate, UUID bannedById, String bannedByFullName, boolean isActive) {
        this.id = id;
        this.bannedUserId = bannedUserId;
        this.reason = reason;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bannedById = bannedById;
        this.bannedByFullName = bannedByFullName;
        this.isActive = isActive;
    }

    public String getBannedByFullName() {
        return bannedByFullName;
    }

    public void setBannedByFullName(String bannedByFullName) {
        this.bannedByFullName = bannedByFullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getBannedUserId() {
        return bannedUserId;
    }

    public void setBannedUserId(UUID bannedUserId) {
        this.bannedUserId = bannedUserId;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public UUID getBannedById() {
        return bannedById;
    }

    public void setBannedById(UUID bannedById) {
        this.bannedById = bannedById;
    }

    public BanResponse() {
    }
}
