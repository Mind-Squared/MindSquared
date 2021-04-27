package com.example.dshbrd;

public class CalendarElement {
    private String elementTitle;
    private String elementTime;

    public CalendarElement(String elementTitle, String elementTime) {
        this.elementTitle = elementTitle;
        this.elementTime= elementTime;
    }

    public String getElementTitle() {
        return elementTitle;
    }
    public String getElementTime(){return elementTime;}

    public void setElementTitle(String elementTitle) {
        this.elementTitle = elementTitle;
    }
    public void steElementTime(String elementTime)
    {
        this.elementTime = elementTime;
    }
}

