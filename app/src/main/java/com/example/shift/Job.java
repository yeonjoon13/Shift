package com.example.shift;

import android.widget.ImageView;

public class Job {
    private int jobId;
    private String role;
    private String company;
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

    public Job() { }


    public Job(String role, String company, String description, String date, String address,
               String time, String pay, boolean training, int imageId) {
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
        this.saved = false;
        this.completed = false;
    }

    public String getRole() { return this.role; }

    public boolean getSaved() { return this.saved; }

    public String getCompany() { return this.company; }

    public boolean getchecked_in() { return this.checked_in; }

    public String getDescription() { return this.description; }

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

    public boolean getTraining() { return this.training; }

    public boolean getCompleted() { return this.completed; }

    public void setchecked_in() {
        this.checked_in = !this.checked_in;
    }

    public void setSaved() { this.saved = !this.saved; }
    public int getImageId() { return imageId; }

    public void setCompleted() { this.completed = !this.completed; }
}
