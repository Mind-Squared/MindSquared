package com.example.dshbrd;

public class RezultatUser {

    String firstname;
    String lastname;
    String correct_questions_answered;
    String nrQuestions;

    public RezultatUser(){

    }

    public RezultatUser(String firstname, String lastname, String correct_questions_answered, String nrQuestions){

        this.firstname = firstname;
        this.lastname = lastname;
        this. correct_questions_answered = correct_questions_answered;
        this. nrQuestions = nrQuestions;

    }

    public String getCorrect_questions_answered() {
        return correct_questions_answered;
    }

    public void setCorrect_questions_answered(String correct_questions_answered) {
        this.correct_questions_answered = correct_questions_answered;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNrQuestions() {
        return nrQuestions;
    }

    public void setNrQuestions(String nrQuestions) {
        this.nrQuestions = nrQuestions;
    }
}
