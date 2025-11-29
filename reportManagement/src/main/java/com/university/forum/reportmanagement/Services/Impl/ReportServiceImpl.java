package com.university.forum.reportmanagement.Services.Impl;

import com.university.forum.reportmanagement.Dtos.Messages.ReportMessage;
import com.university.forum.reportmanagement.Dtos.Requests.ReportRequest;
import com.university.forum.reportmanagement.Dtos.Responses.ReportResponse;
import com.university.forum.reportmanagement.Models.Enums.ReportStatus;
import com.university.forum.reportmanagement.Models.Report;
import com.university.forum.reportmanagement.Repositories.ReportRepository;
import com.university.forum.reportmanagement.Services.ReportService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public void processReport(ReportMessage reportMessage) {
        Report report = new Report();
        report.setReporterId(reportMessage.getReportedId());
        report.setTargetId(reportMessage.getTargetId());
        report.setReason(reportMessage.getReason());
        report.setTargetType(reportMessage.getTargetType());
        report.setTargetSnippet(reportMessage.getTargetSnippet());

        reportRepository.save(report);

    }

    @Override
    public List<ReportResponse> getReports() {

        List<Report> reports = reportRepository.findAll();
        return reports.stream().map(ReportResponse::ReportToReportResponse).toList();
    }

    @Override
    public void updateReport(UUID adminId, UUID id, ReportRequest reportRequest) {
        System.out.println("update service");
        Report report = reportRepository.findById(id).orElse(null);
        if (report == null) {
            throw  new BadCredentialsException("Report not found");
        }
        report.setHandledBy(adminId);
        report.setHandledAt(new Timestamp(System.currentTimeMillis()));
        if(reportRequest.getStatus()!=null) report.setStatus(ReportStatus.valueOf(reportRequest.getStatus()));
        if(reportRequest.getTargetSnippet()!=null && !reportRequest.getTargetSnippet().isBlank()) report.setTargetSnippet(reportRequest.getTargetSnippet());
        if(reportRequest.getAdminFullName()!= null && !reportRequest.getAdminFullName().isBlank()) report.setAdminFullName(reportRequest.getAdminFullName());

        reportRepository.save(report);

    }

    @Override
    public ReportResponse getReportById(UUID id) {
        Report report=reportRepository.findById(id).orElseThrow(
                ()->new BadCredentialsException("Report not found")
        );
        return ReportResponse.ReportToReportResponse(report);
    }
}
