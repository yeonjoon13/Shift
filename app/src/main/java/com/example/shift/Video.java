package com.example.shift;

public class Video {
    private boolean watched;

    private String title;

    private String video;

    private int thumbnail;

    private String videoLink;

    public Video(){}

    public Video(String title, int thumbnail, String link) {
        this.watched = false;
        this.title = title;
        this.thumbnail = thumbnail;
        this.videoLink = link;
    }

    public boolean getWatched() { return this.watched; }

    public String getTitle() { return this.title; }
    public void setWatched() { this.watched = true; }

    public int getThumbnail() { return this.thumbnail; }

    public String getVideoLink() { return this.videoLink; }

}
