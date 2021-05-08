package com.example.dshbrd;

import java.util.ArrayList;

public class User {
    public String email;
    public String type;
    public String firstname , lastname;

    public static class data{
        public int an, zi , luna;

        public data(int z , int l , int a){
            this.an = a;
            this.zi = z;
            this.luna = l;
        }

        public data(){

        }
    }
    public data birthdate;
    ArrayList<JoinedClass_Chat> clase = new ArrayList<JoinedClass_Chat>();
    public User( String email , String type , String firstname , String lastname, int z, int l , int a) {
        this.email = email;
        this.type = type;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = new data(z, l, a);
        this.clase.add(null);
    }
    public User( String email , String type , String firstname , String lastname, int z, int l , int a , ArrayList<JoinedClass_Chat> c) {
        this.email = email;
        this.type = type;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = new data(z, l, a);
        this.clase = c;
    }
    public User() {
    }
}
