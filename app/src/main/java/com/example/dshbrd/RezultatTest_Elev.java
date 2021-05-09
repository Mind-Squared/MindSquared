package com.example.dshbrd;

public class RezultatTest_Elev {

    String titleTest;
    String nrQuestions;
    String correct_questions_answered;
    String test_id;

    public RezultatTest_Elev(){

    }

    public RezultatTest_Elev(String titleTest, String nrQuestions, String correct_questions_answered, String test_id){

        this.titleTest = titleTest;
        this.nrQuestions = nrQuestions;
        this.correct_questions_answered = correct_questions_answered;
        this.test_id = test_id;

    }

    public String getTitleTest() {
        return titleTest;
    }

    public void setTitleTest(String titleTest) {
        this.titleTest = titleTest;
    }

    public String getCorrect_questions_answered() {
        return correct_questions_answered;
    }

    public void setCorrect_questions_answered(String correct_questions_answered) {
        this.correct_questions_answered = correct_questions_answered;
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
