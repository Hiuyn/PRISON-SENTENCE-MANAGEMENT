package com.example.psmsystem.model.prisoner;

public class Prisoner {
    private String prisonerCode;
    private String prisonerName;
    private String DOB;
    private String Gender;
    private String contactName;
    private String contactPhone;
    private String imagePath;

    public Prisoner(){}

    public Prisoner(String prisonerCode, String prisonerName, String DOB, String gender, String contactName, String contactPhone, String imagePath) {
        this.prisonerCode = prisonerCode;
        this.prisonerName = prisonerName;
        this.DOB = DOB;
        Gender = gender;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.imagePath = imagePath;
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

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
