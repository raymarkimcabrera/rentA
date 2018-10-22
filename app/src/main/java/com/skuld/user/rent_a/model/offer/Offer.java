package com.skuld.user.rent_a.model.offer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skuld.user.rent_a.model.car.Car;

import java.io.Serializable;
import java.util.Date;

public class Offer implements Serializable {


    @Expose
    @SerializedName("driverID")
    private String driverID;

    @Expose
    @SerializedName("car")
    private Car car;

    @Expose
    @SerializedName("price")
    private int price;

    @Expose
    @SerializedName("createdAt")
    private Date createdAt;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
