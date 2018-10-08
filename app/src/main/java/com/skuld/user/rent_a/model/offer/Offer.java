package com.skuld.user.rent_a.model.offer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Offer implements Serializable {

    @Expose
    @SerializedName("driverID")
    private String driverID;

    @Expose
    @SerializedName("carID")
    private String carID;

    @Expose
    @SerializedName("price")
    private Double price;

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

}
