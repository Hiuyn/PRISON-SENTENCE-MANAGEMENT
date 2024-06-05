package com.example.psmsystem.model.sentence;

public class Sentence {
    private String prisonerCode;
    private String prisonerName;
    private String sentenceType;
    private String sentenceCode;
    private String startDate;
    private String endDate;
    private String status;
    private String paroleEligibility;

    public Sentence() {
    }

    public Sentence(String prisonerCode, String prisonerName, String sentenceType, String sentenceCode, String startDate, String endDate, String status, String paroleEligibility) {
        this.prisonerCode = prisonerCode;
        this.prisonerName = prisonerName;
        this.sentenceType = sentenceType;
        this.sentenceCode = sentenceCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.paroleEligibility = paroleEligibility;
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

    public String getPrisonerName() {
        return prisonerName;
    }

    public void setPrisonerName(String prisonerName) {
        this.prisonerName = prisonerName;
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
//public class Sentence {
//    private int prisonerId;
//    private int sentenceCode;
//    private String sentenceType;
//    private Date startDate;
//    private Date endDate;
//    private Date updateDate;
//    private Date releaseDate;
//    private boolean status;
//    private String parole;
//    private int userId;
//
//
//    public Sentence(){}
//    public Sentence(int prisonerId, int sentenceCode, String sentenceType, Date startDate, Date endDate, Date updateDate, Date releaseDate, boolean status, String parole, int userId) {
//        this.prisonerId = prisonerId;
//        this.sentenceCode = sentenceCode;
//        this.sentenceType = sentenceType;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.updateDate = updateDate;
//        this.releaseDate = releaseDate;
//        this.status = status;
//        this.parole = parole;
//        this.userId = userId;
//    }
//
//    public int getPrisonerId() {
//        return prisonerId;
//    }
//
//    public void setPrisonerId(int prisonerId) {
//        this.prisonerId = prisonerId;
//    }
//
//    public int getSentenceCode() {
//        return sentenceCode;
//    }
//
//    public void setSentenceCode(int sentenceCode) {
//        this.sentenceCode = sentenceCode;
//    }
//
//    public String getSentenceType() {
//        return sentenceType;
//    }
//
//    public void setSentenceType(String sentenceType) {
//        this.sentenceType = sentenceType;
//    }
//
//    public java.sql.Date getStartDate() {
//        return (java.sql.Date) startDate;
//    }
//
//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }
//
//    public java.sql.Date getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(Date endDate) {
//        this.endDate = endDate;
//    }
//
//    public Date getUpdateDate() {
//        return updateDate;
//    }
//
//    public void setUpdateDate(Date updateDate) {
//        this.updateDate = updateDate;
//    }
//
//    public Date getReleaseDate() {
//        return releaseDate;
//    }
//
//    public void setReleaseDate(Date releaseDate) {
//        this.releaseDate = releaseDate;
//    }
//
//    public boolean isStatus() {
//        return status;
//    }
//
//    public void setStatus(boolean status) {
//        this.status = status;
//    }
//
//    public String getParole() {
//        return parole;
//    }
//
//    public void setParole(String parole) {
//        this.parole = parole;
//    }
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//}
