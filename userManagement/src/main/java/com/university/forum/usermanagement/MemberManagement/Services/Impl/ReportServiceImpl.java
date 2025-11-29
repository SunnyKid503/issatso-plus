package com.university.forum.usermanagement.MemberManagement.Services.Impl;

import com.university.forum.usermanagement.MemberManagement.Repositories.ReportRepository;
import com.university.forum.usermanagement.MemberManagement.Services.ReportService;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
}
