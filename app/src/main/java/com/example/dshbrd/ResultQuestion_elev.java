package com.example.dshbrd;

public class ResultQuestion_elev {

    String answer_correct;
    String titleQuestion;
    String user_answer;

    public ResultQuestion_elev(){

    }

    public ResultQuestion_elev(String answer_correct, String titleQuestion, String user_answer){

        this.answer_correct = answer_correct;
        this.titleQuestion = titleQuestion;
        this.user_answer = user_answer;
    }

    public String getAnswer_correct() {
        return answer_correct;
    }

    public void setAnswer_correct(String answer_correct) {
        this.answer_correct = answer_correct;
    }

    public String getTitleQuestion() {
        return titleQuestion;
    }

    public void setTitleQuestion(String titleQuestion) {
        this.titleQuestion = titleQuestion;
    }

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }
}
