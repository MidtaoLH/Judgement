package com.example.fv.judgement.app.model;

public class LeaveStatusModel {
    private String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getLeaveID() {
        return LeaveID;
    }

    public void setLeaveID(String leaveID) {
        LeaveID = leaveID;
    }

    public String getProcessID() {
        return ProcessID;
    }

    public void setProcessID(String processID) {
        ProcessID = processID;
    }

    public String getApplyCode() {
        return ApplyCode;
    }

    public void setApplyCode(String applyCode) {
        ApplyCode = applyCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String LeaveID;
    private String ProcessID;
    private String ApplyCode;
    private String message;
}
