package com.university.forum.usermanagement.MemberManagement.Models;

import com.university.forum.usermanagement.AdminManagement.Models.Admin;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Ban {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member bannedUser;

    private String reason;
    private LocalDateTime startDate = LocalDateTime.now();
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Admin bannedBy;

    private Boolean isActive = true;

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;


    public Ban(Long id, Member bannedUser, String reason, LocalDateTime startDate, LocalDateTime endDate, Admin bannedBy, Boolean isActive, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.bannedUser = bannedUser;
        this.reason = reason;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bannedBy = bannedBy;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getBannedUser() {
        return bannedUser;
    }

    public void setBannedUser(Member bannedUser) {
        this.bannedUser = bannedUser;
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

    public Admin getBannedBy() {
        return bannedBy;
    }

    public void setBannedBy(Admin bannedBy) {
        this.bannedBy = bannedBy;
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

    public Ban() {
    }
}