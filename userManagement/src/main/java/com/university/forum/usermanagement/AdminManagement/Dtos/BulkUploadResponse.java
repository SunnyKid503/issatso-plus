package com.university.forum.usermanagement.AdminManagement.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public class BulkUploadResponse {
    private int totalProcessed;
    private List<String> errors;
    private int successCount;
    private int errorCount;
    private boolean hasPartialSuccess;

    public BulkUploadResponse(int totalProcessed, List<String> errors, int successCount, int errorCount) {
        this.totalProcessed = totalProcessed;
        this.errors = errors;
        this.successCount = successCount;
        this.errorCount = errorCount;
        this.hasPartialSuccess = successCount > 0 && errorCount > 0;
    }

    public BulkUploadResponse() {
    }

    public int getTotalProcessed() {
        return totalProcessed;
    }

    public void setTotalProcessed(int totalProcessed) {
        this.totalProcessed = totalProcessed;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public boolean isHasPartialSuccess() {
        return hasPartialSuccess;
    }

    public void setHasPartialSuccess(boolean hasPartialSuccess) {
        this.hasPartialSuccess = hasPartialSuccess;
    }
}