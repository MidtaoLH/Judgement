package com.example.fv.judgement.app.model;

import zuo.biao.library.base.BaseModel;

public class AgentListModel extends BaseModel {

   public String AgentG_Cname;
    public String AgentDate;
    public String AgentStartDate;
    public String AgentEndDate;
    public String DocumentTypeNM;
    public String AgentStatusNM;
    public String ApplyManPhoto;
    public String AgentSetID;
    public String AgentStatus;

    public String getAgentG_Cname() {
        return AgentG_Cname;
    }

    public void setAgentG_Cname(String agentG_Cname) {
        AgentG_Cname = agentG_Cname;
    }

    public String getAgentDate() {
        return AgentDate;
    }

    public void setAgentDate(String agentDate) {
        AgentDate = agentDate;
    }

    public String getAgentStartDate() {
        return AgentStartDate;
    }

    public void setAgentStartDate(String agentStartDate) {
        AgentStartDate = agentStartDate;
    }

    public String getAgentEndDate() {
        return AgentEndDate;
    }

    public void setAgentEndDate(String agentEndDate) {
        AgentEndDate = agentEndDate;
    }

    public String getDocumentTypeNM() {
        return DocumentTypeNM;
    }

    public void setDocumentTypeNM(String documentTypeNM) {
        DocumentTypeNM = documentTypeNM;
    }

    public String getAgentStatusNM() {
        return AgentStatusNM;
    }

    public void setAgentStatusNM(String agentStatusNM) {
        AgentStatusNM = agentStatusNM;
    }

    public String getApplyManPhoto() {
        return ApplyManPhoto;
    }

    public void setApplyManPhoto(String applyManPhoto) {
        ApplyManPhoto = applyManPhoto;
    }

    public String getAgentSetID() {
        return AgentSetID;
    }

    public void setAgentSetID(String agentSetID) {
        AgentSetID = agentSetID;
    }

    public String getAgentStatus() {
        return AgentStatus;
    }

    public void setAgentStatus(String agentStatus) {
        AgentStatus = agentStatus;
    }

    @Override
    protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
        return id > 0;// && StringUtil.isNotEmpty(phone, true);
    }
}
