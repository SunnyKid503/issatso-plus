package com.university.forum.usermanagement.MemberManagement.Dtos.Request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class BanRequest {

    private UUID bannedUserId;

    String reason;

    LocalDateTime endDate;

    UUID bannedById;

    private Boolean isActive ;

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public BanRequest(UUID bannedUserId, String reason, LocalDateTime endDate, UUID bannedById, Boolean isActive) {
        this.bannedUserId = bannedUserId;
        this.reason = reason;
        this.endDate = endDate;
        this.bannedById = bannedById;
        this.isActive = isActive;
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

    public BanRequest() {
    }

    public Boolean isEndDateValid(){
        return (endDate == null) || endDate.isAfter(LocalDateTime.now());
    }
    public Boolean isBanRequestValid(){
        return  bannedUserId != null ;
    }

    @Override
    public String toString() {
        return "BanRequest{" +
                "bannedUserId=" + bannedUserId +
                ", reason='" + reason + '\'' +
                ", endDate=" + endDate +
                ", bannedById=" + bannedById +
                ", isActive=" + isActive +
                '}';
    }
}
