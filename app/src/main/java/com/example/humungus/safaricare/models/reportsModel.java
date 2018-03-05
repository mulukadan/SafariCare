package com.example.humungus.safaricare.models;

/**
 * Created by humungus on 3/1/18.
 */

public class reportsModel {

    private String thumbnail;
    private String username;
    private String date;
    private String noPlate;
    private String MatName;
    private String Sacco;
    private String tweets;

    public reportsModel(){

    }

    public reportsModel(String thumbnail, String title, String date, String noPlate, String matName, String sacco, String tweets) {
        this.thumbnail = thumbnail;
        this.username = title;
        this.date = date;
        this.noPlate = noPlate;
        MatName = matName;
        Sacco = sacco;
        this.tweets = tweets;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNoPlate() {
        return noPlate;
    }

    public void setNoPlate(String noPlate) {
        this.noPlate = noPlate;
    }

    public String getMatName() {
        return MatName;
    }

    public void setMatName(String matName) {
        MatName = matName;
    }

    public String getSacco() {
        return Sacco;
    }

    public void setSacco(String sacco) {
        Sacco = sacco;
    }

    public String getTweets() {
        return tweets;
    }

    public void setTweets(String tweets) {
        this.tweets = tweets;
    }

}
