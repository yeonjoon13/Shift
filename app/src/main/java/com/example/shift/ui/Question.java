package com.example.shift.ui;

import java.util.ArrayList;
import java.util.Random;

public class Question {
    private ArrayList<String> qBank;
    private String question;
    private String correctAns;

    public Question() {
    }

    public Question(ArrayList<String> qBank, String question, String correctAns) {
        this.qBank = qBank;
        this.question = question;
        this.correctAns = correctAns;
    }

    public int checkQuestion(String selection) {
        if(selection.equals(correctAns)) {
            return 1;
        }
        return 0;
    }

    public ArrayList<String> getqBank() {
        return qBank;
    }

    public void setqBank(ArrayList<String> qBank) {
        this.qBank = qBank;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }


}
