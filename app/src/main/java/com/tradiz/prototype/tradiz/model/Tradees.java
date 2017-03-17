package com.tradiz.prototype.tradiz.model;

/**
 * Created by Saurabh on 3/10/2017.
 */

public class Tradees {

    private String  ImageURL,  FullName;
    private int Rank, Availability ,Rate, Distance;



    public Tradees() {
    }




    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int Rank) {
        this.Rank = Rank;
    }

    public int getRate() {
        return Rate;
    }

    public void setRate(int Rate) {
        this.Rate = Rate;
    }

    public int getDistance() {
        return Distance;
    }

    public void setDistance(int Distance) {
        this.Distance = Distance;
    }

    public int getAvailability() {
        return Availability;
    }

    public void setAvailability(int Availability) {
        this.Availability = Availability;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String ImageURL) {
        this.ImageURL = ImageURL;
    }

}
