package com.example.dshbrd;

public class time_conversatie {
    int an,luna , zi , ora, minut , secunda , milisecunda;
    public time_conversatie(String s){
        this.an = Integer.parseInt(s.substring(0, 3));
        this.luna = Integer.parseInt(s.substring(4, 6));
        this.zi = Integer.parseInt(s.substring(7, 9));
        this.ora = Integer.parseInt(s.substring(10, 12));
        this.minut = Integer.parseInt(s.substring(12, 14));
        this.secunda = Integer.parseInt(s.substring(14, 16));
        this.milisecunda = Integer.parseInt(s.substring(16,18 ));

    }
}
