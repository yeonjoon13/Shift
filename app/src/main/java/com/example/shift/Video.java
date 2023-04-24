package com.example.shift;

public class Video {
    private boolean watched;

    private String title;

    private String video;

    private int thumbnail;

    public Video(){}

    public Video(String title, int thumbnail) {
        this.watched = false;
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public boolean getWatched() { return this.watched; }

    public String getTitle() { return this.title; }
    public void setWatched() { this.watched = true; }

    public int getThumbnail() { return this.thumbnail; }

}
