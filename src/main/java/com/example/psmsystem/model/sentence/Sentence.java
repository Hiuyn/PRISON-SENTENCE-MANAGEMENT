package com.example.psmsystem.model.sentence;

public class Sentence {
    private String prisonerId;
    private String prisonerName;
    private String sentenceCode;
    private String sentenceType;
    private String crimesCode;
    private String startDate;
    private String endDate;
    private String releaseDate;
    private String status;
    private String paroleEligibility;

    public Sentence() {
    }

    public Sentence(String prisonerId, String prisonerName, String sentenceCode, String sentenceType, String crimesCode, String startDate, String endDate, String releaseDate, String status, String paroleEligibility) {
        this.prisonerId = prisonerId;
        this.prisonerName = prisonerName;
        this.sentenceCode = sentenceCode;
        this.sentenceType = sentenceType;
        this.crimesCode = crimesCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.releaseDate = releaseDate;
        this.status = status;
        this.paroleEligibility = paroleEligibility;
    }

    public String getPrisonerId() {
        return prisonerId;
    }

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
    }

    public String getPrisonerName() {
        return prisonerName;
    }

    public void setPrisonerName(String prisonerName) {
        this.prisonerName = prisonerName;
    }

    public String getSentenceCode() {
        return sentenceCode;
    }

    public void setSentenceCode(String sentenceCode) {
        this.sentenceCode = sentenceCode;
    }

    public String getSentenceType() {
        return sentenceType;
    }

    public void setSentenceType(String sentenceType) {
        this.sentenceType = sentenceType;
    }

    public String getCrimesCode() {
        return crimesCode;
    }

    public void setCrimesCode(String crimesCode) {
        this.crimesCode = crimesCode;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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
