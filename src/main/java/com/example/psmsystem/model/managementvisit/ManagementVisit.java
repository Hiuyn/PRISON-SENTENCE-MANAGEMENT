package com.example.psmsystem.model.managementvisit;

public class ManagementVisit {
    private String prisonerCode;
    private String visitorName;
    private String nationalIdentificationNumber;
    private String relationship;
    private String visitDate;
    private String notes;

    public ManagementVisit() {}

    public ManagementVisit(String prisonerCode, String visitorName, String nationalIdentificationNumber, String relationship, String visitDate, String notes) {
        this.prisonerCode = prisonerCode;
        this.visitorName = visitorName;
        this.nationalIdentificationNumber = nationalIdentificationNumber;
        this.relationship = relationship;
        this.visitDate = visitDate;
        this.notes = notes;
    }

    public String getPrisonerCode() {
        return prisonerCode;
    }

    public void setPrisonerCode(String prisonerCode) {
        this.prisonerCode = prisonerCode;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getNationalIdentificationNumber() {
        return nationalIdentificationNumber;
    }

    public void setNationalIdentificationNumber(String nationalIdentificationNumber) {
        this.nationalIdentificationNumber = nationalIdentificationNumber;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
