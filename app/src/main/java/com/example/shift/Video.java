package com.example.shift;

public class Video {
    private boolean watched;

    private String title;

    private String video;

    public Video(){}

    public Video(String title) {
        this.watched = false;
        this.title = title;

    }

    public boolean getWatched() { return this.watched; }

    public String getTitle() { return this.title; }
    public void setWatched() { this.watched = true; }

}
