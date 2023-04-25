package com.example.shift;

import android.widget.ImageView;

import com.example.shift.ui.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Job {
    private int jobId;
    private String role;
    private String company;
    private int jobType;
    private boolean checked_in;

    private int imageId;

    private String description;
    private String date;
    private String address;
    private String time;
    private String pay;
    private boolean training;

    private boolean saved;

    private boolean completed;
    private double distance;
    private boolean haveQuestions;
    private ArrayList<Question> questions;
    private int requiredCorrect;
    private boolean questionsCompleted;

    public Job() {
    }


    public Job(String role, int jobType, String company, String description, String date, String address,
               String time, String pay, boolean training, double distance, int imageId, ArrayList<Question> questions, int requiredCorrect) {
        Random rand = new Random();
        this.jobId = rand.nextInt(10000);

        // 1 = Fast Food 2 = warehouse 3 = cafe 4 = transportation
        this.jobType = jobType;
        this.role = role;
        this.company = company;
        this.checked_in = false;
        this.description = description;
        this.date = date;
        this.address = address;
        this.time = time;
        this.pay = pay;
        this.training = training;
        this.imageId = imageId;
        this.distance = distance;
        this.saved = false;
        this.completed = false;
        this.haveQuestions = false;
        this.questions = new ArrayList<>();
        this.requiredCorrect = requiredCorrect;
        questionsCompleted = false;

    }

    public int getJobId() {
        return this.jobId;
    }

    public int getJobType() {
        return this.jobType;
    }

    public String getRole() {
        return this.role;
    }

    public boolean getSaved() {
        return this.saved;
    }

    public String getCompany() {
        return this.company;
    }

    public boolean getchecked_in() {
        return this.checked_in;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDate() {
        return this.date;
    }

    public String getAddress() {
        return this.address;
    }

    public String getTime() {
        return this.time;
    }

    public String getPay() {
        return this.pay;
    }

    public boolean getTraining() {
        return this.training;
    }

    public boolean getCompleted() {
        return this.completed;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setchecked_in() {
        this.checked_in = !this.checked_in;
    }

    public void setSaved() {
        this.saved = !this.saved;
    }

    public int getImageId() {
        return imageId;
    }

    public void setCompleted() {
        this.completed = !this.completed;
    }

    public boolean getHaveQuestions() {
        return this.haveQuestions;
    }

    public void setHaveQuestions() {
        this.haveQuestions = !this.haveQuestions;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public int getRequiredCorrect() {
        return requiredCorrect;
    }

    public void setRequiredCorrect(int requiredCorrect) {
        this.requiredCorrect = requiredCorrect;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public boolean isQuestionsCompleted() {
        return questionsCompleted;
    }

    public void setQuestionsCompleted() {
        this.questionsCompleted = !questionsCompleted;
    }
}
