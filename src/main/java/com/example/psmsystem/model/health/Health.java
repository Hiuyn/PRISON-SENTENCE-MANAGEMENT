package com.example.psmsystem.model.health;

public class Health {
    private String prisonerCode;
    private String prisonerName;
    private Double weight;
    private Double height;
    private String checkupDate;
    private String physicalCondition;
    private String psychologicalSigns;
    private String situation;
    private String notes;

    public Health() {}

    public Health(String prisonerCode, String prisonerName, Double weight, Double height, String checkupDate, String physicalCondition, String psychologicalSigns, String situation, String notes) {
        this.prisonerCode = prisonerCode;
        this.prisonerName = prisonerName;
        this.weight = weight;
        this.height = height;
        this.checkupDate = checkupDate;
        this.physicalCondition = physicalCondition;
        this.psychologicalSigns = psychologicalSigns;
        this.situation = situation;
        this.notes = notes;
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

    public String getPhysicalCondition() {
        return physicalCondition;
    }

    public void setPhysicalCondition(String physicalCondition) {
        this.physicalCondition = physicalCondition;
    }

    public String getPsychologicalSigns() {
        return psychologicalSigns;
    }

    public void setPsychologicalSigns(String psychologicalSigns) {
        this.psychologicalSigns = psychologicalSigns;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
