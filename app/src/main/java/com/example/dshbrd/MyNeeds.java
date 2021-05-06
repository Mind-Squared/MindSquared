package com.example.dshbrd;

public class MyNeeds {

    String titledoes;
    String datedoes;
    String descdoes;
    String keydoes;
    String timedoes;

    public MyNeeds() {
    }

    public MyNeeds(String titledoes, String datedoes, String descdoes, String keydoes, String timedoes) {
        this.titledoes = titledoes;
        this.datedoes = datedoes;
        this.descdoes = descdoes;
        this.timedoes = timedoes;
        this.keydoes = keydoes;
    }

    public String getKeydoes() {
        return keydoes;
    }

    public void setKeydoes(String keydoes) {
        this.keydoes = keydoes;
    }

    public String getTitledoes() {
        return titledoes;
    }

    public void setTitledoes(String titledoes) {
        this.titledoes = titledoes;
    }

    public String getDatedoes() {
        return datedoes;
    }

    public void setDatedoes(String datedoes) {
        this.datedoes = datedoes;
    }

    public String getTimedoes(){return timedoes;}

    public void setTimedoes(String timedoes) { this.timedoes = timedoes;}

    public String getDescdoes() {
        return descdoes;
    }

    public void setDescdoes(String descdoes) {
        this.descdoes = descdoes;
    }


}
