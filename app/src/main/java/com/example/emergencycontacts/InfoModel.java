package com.example.emergencycontacts;

public class InfoModel {
    private String description;
    private int image;
    private String title;

    public InfoModel(int image2, String title2, String description2) {
        this.image = image2;
        this.title = title2;
        this.description = description2;
    }

    public void setImage(int image2) {
        this.image = image2;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public void setDescription(String description2) {
        this.description = description2;
    }

    public int getImage() {
        return this.image;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }
}
