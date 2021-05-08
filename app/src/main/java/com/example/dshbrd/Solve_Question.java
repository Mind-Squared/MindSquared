package com.example.dshbrd;

public class Solve_Question {

    String titleQuestion;
    String answer_A;
    String answer_B;
    String answer_C;
    String answer_D;
    String answer_correct;
    String question_id;
    String question_creator;
    String test_id;

    public Solve_Question(){

    }

    public Solve_Question(String titleQuestion, String answer_A, String answer_B, String answer_C, String answer_D, String question_id, String question_creator, String test_id, String answer_correct){

        this.titleQuestion = titleQuestion;
        this.answer_A = answer_A;
        this.answer_B = answer_B;
        this.answer_C = answer_C;
        this.answer_D = answer_D;
        this.question_id = question_id;
        this.question_creator = question_creator;
        this.test_id =test_id;
        this.answer_correct = answer_correct;

    }

    public String getTitleQuestion() {
        return titleQuestion;
    }

    public void setTitleQuestion(String titleQuestion) {
        this.titleQuestion = titleQuestion;
    }

    public String getAnswer_A() {
        return answer_A;
    }

    public void setAnswer_A(String answer_A) {
        this.answer_A = answer_A;
    }

    public String getAnswer_B() {
        return answer_B;
    }

    public void setAnswer_B(String answer_B) {
        this.answer_B = answer_B;
    }

    public String getAnswer_C() {
        return answer_C;
    }

    public void setAnswer_C(String answer_C) {
        this.answer_C = answer_C;
    }

    public String getAnswer_D() {
        return answer_D;
    }

    public void setAnswer_D(String answer_D) {
        this.answer_D = answer_D;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_creator() {
        return question_creator;
    }

    public void setQuestion_creator(String question_creator) {
        this.question_creator = question_creator;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getAnswer_correct() {
        return answer_correct;
    }

    public void setAnswer_correct(String answer_correct) {
        this.answer_correct = answer_correct;
    }
}
