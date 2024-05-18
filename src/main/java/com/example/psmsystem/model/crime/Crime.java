package com.example.psmsystem.model.crime;

public class Crime {
    private String sentenceCode;

    public Crime(){}

    public Crime(String sentenceCode) {
        this.sentenceCode = sentenceCode;
    }

    public String getSentenceCode() {
        return sentenceCode;
    }

    public void setSentenceCode(String sentenceCode) {
        this.sentenceCode = sentenceCode;
    }
}
