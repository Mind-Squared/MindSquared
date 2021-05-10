package com.example.dshbrd;

public class CalendarElement {
    private String titledoes;
    private String datadoes;
    private String timedoes;

    public CalendarElement(String elementTitle, String elementDate, String elementTime) {
        this.titledoes = elementTitle;
        this.datadoes = elementDate;
        this.timedoes= elementTime;
    }

    public String getElementTitle() {
        return titledoes;
    }
    public String getElementDate(){return datadoes;}
    public String getElementTime(){return timedoes;}

    public void setElementTitle(String elementTitle) {
        this.titledoes = elementTitle;
    }
    public void steElementTime(String elementTime)
    {
        this.timedoes = elementTime;
    }
    public void setElementData(String elementData)
    {
        this.datadoes = elementData;
    }
}

