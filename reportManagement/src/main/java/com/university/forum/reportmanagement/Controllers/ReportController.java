package com.university.forum.reportmanagement.Controllers;

import com.university.forum.reportmanagement.Dtos.Requests.ReportRequest;
import com.university.forum.reportmanagement.Services.ReportService;
import com.university.forum.reportmanagement.Utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reportmanagement/reports")
public class ReportController {

    private final static Logger logger= LoggerFactory.getLogger(ReportController.class);
    private final ReportService reportService;
    private final JwtTokenUtil jwtTokenUtil;

    public ReportController(ReportService reportService, JwtTokenUtil jwtTokenUtil) {
        this.reportService = reportService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/")
    public ResponseEntity<?> getReports() {
        return ResponseEntity.ok(Map.of("reports",reportService.getReports()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable UUID id) {
        if(id==null){
            return ResponseEntity.badRequest().body("id is null");
        }
        return ResponseEntity.ok(Map.of("report",reportService.getReportById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateReport(@PathVariable("id") UUID id,@RequestHeader("Authorization") String auth ,@RequestBody ReportRequest reportRequest) {
        String token=auth.replace("Bearer ","");
        UUID adminId= jwtTokenUtil.getUserIdFromToken(token);
        System.out.println("admin id :"+adminId);
        Map<String,Object> response=new HashMap<>();
        if (adminId==null) {
            response.put("message", "adminId must be greater than 0");
            return ResponseEntity.badRequest().body(response);
        }
        if (!reportRequest.isValid()){
            response.put("message","Invalid report request");
            return ResponseEntity.badRequest().body(response);
        }
        reportService.updateReport(adminId,id,reportRequest);
        response.put("message", "Report updated successfully");
        return ResponseEntity.ok(response);
    }

}
