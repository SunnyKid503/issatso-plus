package com.university.forum.reportmanagement.Dtos.Requests;


import com.university.forum.reportmanagement.Models.Enums.ReportStatus;
import java.util.Arrays;

public class ReportRequest {


    private String targetSnippet;
    private String status;
    private String adminFullName;

    public String getTargetSnippet() {
        return targetSnippet;
    }

    public void setTargetSnippet(String targetSnippet) {
        this.targetSnippet = targetSnippet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdminFullName() {
        return adminFullName;
    }

    public void setAdminFullName(String adminFullName) {
        this.adminFullName = adminFullName;
    }

    public ReportRequest(String targetSnippet, String status, String adminFullName) {
        this.targetSnippet = targetSnippet;
        this.status = status;
        this.adminFullName = adminFullName;
    }

    public ReportRequest() {
    }

    public boolean isValid() {
        System.out.println("aaaaaaaaaaaaa");
        return (targetSnippet != null && !targetSnippet.isEmpty()) ||
                (status != null && isValidReportStatus(status));
    }

    public static boolean isValidReportStatus(String status) {
        System.out.println("EEEEEEEEEEEEEEE");
        return Arrays.stream(ReportStatus.values())
                .anyMatch(enumValue -> enumValue.name().equalsIgnoreCase(status));

    }


}
