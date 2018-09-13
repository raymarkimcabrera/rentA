package com.skuld.user.rent_a.model.car;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Car implements Serializable {

    @Expose
    @SerializedName("driverID")
    String driverID;

    @Expose
    @SerializedName("carModel")
    String carModel;

    @Expose
    @SerializedName("imageUrl")
    String imageUrl;

    @Expose
    @SerializedName("maximumPassenger")
    int maximumPassenger;

    @Expose
    @SerializedName("driverChoice")
    String driverChoice;

    @Expose
    @SerializedName("lowPrice")
    double lowPrice;

    @Expose
    @SerializedName("highPrice")
    double highPrice;

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getMaximumPassenger() {
        return maximumPassenger;
    }

    public void setMaximumPassenger(int maximumPassenger) {
        this.maximumPassenger = maximumPassenger;
    }

    public String getDriverChoice() {
        return driverChoice;
    }

    public void setDriverChoice(String driverChoice) {
        this.driverChoice = driverChoice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
