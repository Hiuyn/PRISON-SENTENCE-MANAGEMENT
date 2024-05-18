package com.example.psmsystem.model.sentence;

public class Sentence {
    private String prisonerCode;
    private String sentenceType;
    private String sentenceCode;
    private String startDate;
    private String endDate;
    private String status;
    private String paroleEligibility;

    public Sentence() {
    }

    public Sentence(String prisonerCode, String sentenceType, String sentenceCode, String startDate, String endDate, String status, String paroleEligibility) {
        this.prisonerCode = prisonerCode;
        this.sentenceType = sentenceType;
        this.sentenceCode = sentenceCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.paroleEligibility = paroleEligibility;
    }

    public String getPrisonerCode() {
        return prisonerCode;
    }

    public void setPrisonerCode(String prisonerCode) {
        this.prisonerCode = prisonerCode;
    }

    public String getSentenceType() {
        return sentenceType;
    }

    public void setSentenceType(String sentenceType) {
        this.sentenceType = sentenceType;
    }

    public String getSentenceCode() {
        return sentenceCode;
    }

    public void setSentenceCode(String sentenceCode) {
        this.sentenceCode = sentenceCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParoleEligibility() {
        return paroleEligibility;
    }

    public void setParoleEligibility(String paroleEligibility) {
        this.paroleEligibility = paroleEligibility;
    }
}
