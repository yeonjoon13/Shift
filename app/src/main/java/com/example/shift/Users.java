package com.example.shift;

import com.example.shift.Job;

import java.util.ArrayList;

public class Users {
    String firstName, lastName, email;
    ArrayList<Job> master;
    ArrayList<Job>  upcomingJobs;
    ArrayList<Job>  likedJobs;
    ArrayList<Job> recommendedJobs;


    public Users(String firstName, String lastName, String email, ArrayList<Job> upcomingJobs, ArrayList<Job> likedJobs, ArrayList<Job> recommendedJobs) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.upcomingJobs = upcomingJobs;
        this.likedJobs = likedJobs;
        this.recommendedJobs = recommendedJobs;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUpcomingJobs(ArrayList<Job> upcomingJobs) {
        this.upcomingJobs = upcomingJobs;
    }

    public void setLikedJobs(ArrayList<Job> likedJobs) {
        this.likedJobs = likedJobs;
    }

    public void setRecommendedJobs(ArrayList<Job> recommendedJobs) {
        this.recommendedJobs = recommendedJobs;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Job> getUpcomingJobs() {
        return upcomingJobs;
    }

    public ArrayList<Job> getLikedJobs() {
        return likedJobs;
    }

    public ArrayList<Job> getRecommendedJobs() {
        return recommendedJobs;
    }
}