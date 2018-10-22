package com.skuld.user.rent_a.model.car;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Car implements Serializable {

    @Expose
    @SerializedName("id")
    String id;

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
    @SerializedName("transactionID")
    String transactionID;

    @Expose
    @SerializedName("plateNumber")
    String plateNumber;

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
