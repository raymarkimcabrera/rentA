package com.skuld.user.rent_a.model.transaction;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.gson.annotations.SerializedName;


public class Transaction {

    @SerializedName("user_id")
    private String userID;

    @SerializedName("driver_id")
    private String driverID;

    @SerializedName("destination_location_id")
    private String destinationLocationID;

    @SerializedName("pickup_location_id")
    private String pickupLocationID;

    @SerializedName("car_id")
    private String carID;

    @SerializedName("conversation_id")
    private String conversationID;

    @SerializedName("start_date")
    private Timestamp startDate;

    @SerializedName("end_date")
    private Timestamp endDate;

    @SerializedName("total_amount")
    private int totalAmount;

    @SerializedName("payment_status")
    private String paymentStatus;

    @SerializedName("mode_of_payment")
    private String modeOfPayment;

    @SerializedName("rating")
    private int rating;

    @SerializedName("created_date")
    private Timestamp createdDate;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getDestinationLocationID() {
        return destinationLocationID;
    }

    public void setDestinationLocationID(String destinationLocationID) {
        this.destinationLocationID = destinationLocationID;
    }

    public String getPickupLocationID() {
        return pickupLocationID;
    }

    public void setPickupLocationID(String pickupLocationID) {
        this.pickupLocationID = pickupLocationID;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }



    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
