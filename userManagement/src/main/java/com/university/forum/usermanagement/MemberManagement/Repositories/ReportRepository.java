package com.university.forum.usermanagement.MemberManagement.Repositories;

import com.university.forum.usermanagement.MemberManagement.Models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository  extends JpaRepository<Report, Integer> {
}
