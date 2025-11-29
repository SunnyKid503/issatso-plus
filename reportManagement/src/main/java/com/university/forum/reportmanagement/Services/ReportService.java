package com.university.forum.reportmanagement.Services;

import com.university.forum.reportmanagement.Dtos.Messages.ReportMessage;
import com.university.forum.reportmanagement.Dtos.Requests.ReportRequest;
import com.university.forum.reportmanagement.Dtos.Responses.ReportResponse;

import java.util.List;
import java.util.UUID;

public interface ReportService {
    void processReport(ReportMessage reportMessage);

    List<ReportResponse> getReports();

    void updateReport(UUID adminId, UUID id, ReportRequest reportRequest);

    ReportResponse getReportById(UUID id);
}
