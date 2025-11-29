package com.university.forum.reportmanagement.Repositories;

import com.university.forum.reportmanagement.Models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {
}
