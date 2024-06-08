package com.example.psmsystem.model.health;

public class Health {
    private String healthCode;
    private String prisonerId;
    private String sentenceId ;
    private String sentenceCode;
    private String prisonerName;
    private Double weight;
    private Double height;
    private String checkupDate;
    private Boolean status;
    private int level;

    public Health() {}

    public Health(String healthCode, String prisonerId, String sentenceId, String sentenceCode, String prisonerName, Double weight, Double height, String checkupDate, Boolean status, int level) {
        this.healthCode = healthCode;
        this.prisonerId = prisonerId;
        this.sentenceId = sentenceId;
        this.sentenceCode = sentenceCode;
        this.prisonerName = prisonerName;
        this.weight = weight;
        this.height = height;
        this.checkupDate = checkupDate;
        this.status = status;
        this.level = level;
    }

    public String getHealthCode() {
        return healthCode;
    }

    public void setHealthCode(String healthCode) {
        this.healthCode = healthCode;
    }

    public String getPrisonerId() {
        return prisonerId;
    }

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
    }

    public String getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(String sentenceId) {
        this.sentenceId = sentenceId;
    }

    public String getSentenceCode() {
        return sentenceCode;
    }

    public void setSentenceCode(String sentenceCode) {
        this.sentenceCode = sentenceCode;
    }

    public String getPrisonerName() {
        return prisonerName;
    }

    public void setPrisonerName(String prisonerName) {
        this.prisonerName = prisonerName;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getCheckupDate() {
        return checkupDate;
    }

    public void setCheckupDate(String checkupDate) {
        this.checkupDate = checkupDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
