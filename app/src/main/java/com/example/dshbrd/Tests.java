package com.example.dshbrd;

public class Tests {

    String titleTest;
    String clasaTest;
    String nrQuestions;
    String test_id;

    public Tests(){

    }

    public Tests(String titleTest, String clasaTest, String nrQuestions, String test_id){

        this.titleTest = titleTest;
        this.clasaTest = clasaTest;
        this.nrQuestions = nrQuestions;
        this.test_id = test_id;

    }

    public String getTitleTest() {
        return titleTest;
    }

    public void setTitleTest(String titleTest) {
        this.titleTest = titleTest;
    }

    public String getClasaTest() {
        return clasaTest;
    }

    public void setClasaTest(String clasaTest) {
        this.clasaTest = clasaTest;
    }

    public String getNrQuestions() {
        return nrQuestions;
    }

    public void setNrQuestions(String nrQuestions) {
        this.nrQuestions = nrQuestions;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }
}
