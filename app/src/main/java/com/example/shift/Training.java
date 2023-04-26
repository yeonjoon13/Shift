package com.example.shift;

public class Training {

    private String description;

    private String videoLink;

    private int imageId;

    public Training() {

    }
    public Training(String description, String videoLink, int imageId) {
        this.description = description;
        this.videoLink = videoLink;
        this.imageId = imageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
