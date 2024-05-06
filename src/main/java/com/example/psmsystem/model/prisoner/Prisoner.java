package com.example.psmsystem.model.prisoner;

public class Prisoner {
    private String prisonerId;
    private String prisonerName;
    private String imagePrisonerPath;

    public Prisoner(){}

    public Prisoner(String prisonerId, String prisonerName, String imagePrisoner) {
        this.prisonerId = prisonerId;
        this.prisonerName = prisonerName;
        this.imagePrisonerPath = imagePrisoner;
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

    public String getImagePrisonerPath() {
        return imagePrisonerPath;
    }

    public void setImagePrisonerPath(String imagePrisonerPath) {
        this.imagePrisonerPath = imagePrisonerPath;
    }
}
