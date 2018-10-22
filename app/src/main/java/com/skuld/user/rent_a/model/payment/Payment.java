package com.skuld.user.rent_a.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Payment implements Serializable {

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("totalAmount")
    private double totalAmount;

    @Expose
    @SerializedName("status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount < 0 ? 0.00 : totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
