package com.spendwise.report.controller;

import com.spendwise.report.dto.CategoryBreakdownDto;
import com.spendwise.report.dto.DashboardReportDto;
import com.spendwise.report.dto.MonthlyTrendDto;
import com.spendwise.report.entity.BudgetAlert;
import com.spendwise.report.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    private void validateUser(Long userId) {
        if (userId == null || userId <= 0) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User authentication required");
        }
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardReportDto> getDashboardReport(
            @RequestHeader(value = "X-Authenticated-User-Id", required = false) Long userId
    ) {
        validateUser(userId);
        return ResponseEntity.ok(reportService.getDashboardReport(userId));
    }

    @GetMapping("/category-breakdown")
    public ResponseEntity<List<CategoryBreakdownDto>> getCategoryBreakdown(
            @RequestHeader(value = "X-Authenticated-User-Id", required = false) Long userId,
            @RequestParam(value = "yearMonth", required = false) String yearMonth
    ) {
        validateUser(userId);
        return ResponseEntity.ok(reportService.getCategoryBreakdown(userId, yearMonth));
    }

    @GetMapping("/monthly-trends")
    public ResponseEntity<List<MonthlyTrendDto>> getMonthlyTrends(
            @RequestHeader(value = "X-Authenticated-User-Id", required = false) Long userId
    ) {
        validateUser(userId);
        return ResponseEntity.ok(reportService.getMonthlyTrends(userId));
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<BudgetAlert>> getBudgetAlerts(
            @RequestHeader(value = "X-Authenticated-User-Id", required = false) Long userId
    ) {
        validateUser(userId);
        return ResponseEntity.ok(reportService.getBudgetAlerts(userId));
    }
}
