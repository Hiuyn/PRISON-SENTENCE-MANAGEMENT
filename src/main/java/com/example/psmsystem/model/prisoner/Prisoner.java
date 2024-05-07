package com.example.psmsystem.model.prisoner;

public class Prisoner {
    private String prisonerId;
    private String prisonerName;
    private String imagePath;

    public Prisoner(){}

    public Prisoner(String prisonerId, String prisonerName, String imagePath) {
        this.prisonerId = prisonerId;
        this.prisonerName = prisonerName;
        this.imagePath = imagePath;
    }

    public String getPrisonerName() {
        return prisonerName;
    }

    public void setPrisonerName(String prisonerName) {
        this.prisonerName = prisonerName;
    }

    public String getPrisonerId() {
        return prisonerId;
    }

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
