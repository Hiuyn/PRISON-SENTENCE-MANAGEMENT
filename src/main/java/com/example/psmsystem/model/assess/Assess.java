package com.example.psmsystem.model.assess;

public class Assess {
    private String prisonerCode;
    private String prisonerName;
    private String eventDate;
    private String eventType;
    private String desctiption;
    private String note;

    public Assess() {
    }

    public Assess(String prisonerCode, String prisonerName, String eventDate, String eventType, String desctiption, String note) {
        this.prisonerCode = prisonerCode;
        this.prisonerName = prisonerName;
        this.eventDate = eventDate;
        this.eventType = eventType;
        this.desctiption = desctiption;
        this.note = note;
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

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDesctiption() {
        return desctiption;
    }

    public void setDesctiption(String desctiption) {
        this.desctiption = desctiption;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
