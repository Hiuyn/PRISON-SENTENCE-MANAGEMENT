package com.example.psmsystem.model.prisoner;

public class Prisoner {
    private String prisonerCode;
    private String prisonerName;
    private String DOB;
    private int Gender;
    private int identityCard;
    private String contactName;
    private String contactPhone;
    private String imagePath;
    private boolean status;
    private String user_id;


    public Prisoner(){}

    public Prisoner(String prisonerCode, String prisonerName, String DOB, int gender, int identityCard, String contactName, String contactPhone, String imagePath, boolean status, String user_id) {
        this.prisonerCode = prisonerCode;
        this.prisonerName = prisonerName;
        this.DOB = DOB;
        Gender = gender;
        this.identityCard = identityCard;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.imagePath = imagePath;
        this.status = status;
        this.user_id = user_id;
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

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public int getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(int identityCard) {
        this.identityCard = identityCard;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
