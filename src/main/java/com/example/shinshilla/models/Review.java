package com.example.shinshilla.models;

public class Review {
    private String name;
    private int rating;
    private String comment;
    private String date;

    public Review() {}

    public Review(String name, int rating, String comment, String date) {
        this.name = name;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public int getRating(){return rating;}
    public void setRating(int rating){this.rating=rating;}
    public String getComment(){return comment;}
    public void setComment(String comment){this.comment=comment;}
    public String getDate(){return date;}
    public void setDate(String date){this.date=date;}
}
