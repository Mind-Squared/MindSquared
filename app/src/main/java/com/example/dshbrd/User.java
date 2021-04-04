package com.example.dshbrd;

import java.util.ArrayList;

public class User {
    public String username;
    public String email;
    public String type;
    ArrayList<String> clase = new ArrayList<String>();
    public User(String username , String email , String type){
        this.email = email;
        this.username = username;
        this.type = type;
        this.clase.add(" ");
    }
}
