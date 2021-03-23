package com.example.emergencycontacts;

public class DataModel {
    private String comment;
    private String name;
    private String rating;

    public void setName(String name2) {
        this.name = name2;
    }

    public void setComment(String comment2) {
        this.comment = comment2;
    }

    public void setRating(String rating2) {
        this.rating = rating2;
    }

    public String getName() {
        return this.name;
    }

    public String getComment() {
        return this.comment;
    }

    public String getRating() {
        return this.rating;
    }
}
