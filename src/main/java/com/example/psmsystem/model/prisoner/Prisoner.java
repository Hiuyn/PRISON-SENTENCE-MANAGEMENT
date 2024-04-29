package com.example.psmsystem.model.prisoner;

public class Prisoner {
    private String maTN;
    private String nameTN;

    public Prisoner(){}

    public Prisoner(String maTN, String nameTN) {
        this.maTN = maTN;
        this.nameTN = nameTN;
    }

    public String getNameTN() {
        return nameTN;
    }

    public void setNameTN(String nameTN) {
        this.nameTN = nameTN;
    }

    public String getMaTN() {
        return maTN;
    }

    public void setMaTN(String maTN) {
        this.maTN = maTN;
    }
}
