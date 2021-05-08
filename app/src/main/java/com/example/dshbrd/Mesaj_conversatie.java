package com.example.dshbrd;

public class Mesaj_conversatie implements Comparable {
    Destinatar_Conversatie destinatar;
    String text, time;
    public Mesaj_conversatie(String _text , String _time , String _cod , String _firstname , String _lastname){
        this.destinatar = new Destinatar_Conversatie(_cod , _firstname , _lastname);
        this.text = _text;
        this.time = _time;
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
