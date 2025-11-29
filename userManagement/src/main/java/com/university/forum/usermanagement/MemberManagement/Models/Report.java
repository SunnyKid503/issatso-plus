package com.university.forum.usermanagement.MemberManagement.Models;


import com.university.forum.usermanagement.MemberManagement.Models.Enums.ReportStatus;
import com.university.forum.usermanagement.MemberManagement.Models.Enums.ReportType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)  // ✅ Correct
    private Member reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member reportedUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType reportType;

    private String details;
    private ReportStatus status = ReportStatus.PENDING;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Report(Long id, Member reporter, Member reportedUser, ReportType reportType, String details, ReportStatus status) {
        this.id = id;
        this.reporter = reporter;
        this.reportedUser = reportedUser;
        this.reportType = reportType;
        this.details = details;
        this.status = status;
    }

    public Member getReporter() {
        return reporter;
    }

    public void setReporter(Member reporter) {
        this.reporter = reporter;
    }

    public Member getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(Member reportedUser) {
        this.reportedUser = reportedUser;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public Report() {
    }
}