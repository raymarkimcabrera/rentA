package com.skuld.user.rent_a.model.transaction;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Transaction implements Serializable{

    @Expose
    @SerializedName("user_id")
    private String userID;

    @Expose
    @SerializedName("driver_id")
    private String driverID;

    @Expose
    @SerializedName("payment_id")
    private String paymentID;

    @Expose
    @SerializedName("destination_location_id")
    private String destinationLocationID;

    @Expose
    @SerializedName("pickup_location_id")
    private String pickupLocationID;

    @Expose
    @SerializedName("car_id")
    private String carID;

    @Expose
    @SerializedName("conversation_id")
    private String conversationID;

    @Expose
    @SerializedName("start_date")
    private Timestamp startDate;

    @Expose
    @SerializedName("end_date")
    private Timestamp endDate;

    @Expose
    @SerializedName("total_amount")
    private int totalAmount;

    @Expose
    @SerializedName("payment_status")
    private String paymentStatus;

    @Expose
    @SerializedName("mode_of_payment")
    private String modeOfPayment;

    @Expose
    @SerializedName("rating")
    private int rating;

    @Expose
    @SerializedName("created_date")
    private Timestamp createdDate;

    @Expose
    @SerializedName("passengers")
    private int passengers;

    @Expose
    @SerializedName("is_with_driver")
    private boolean isWithDriver;

    @Expose
    @SerializedName("type_of_vehicle")
    private String typeOfVehicle;

    @Expose
    @SerializedName("type_of_payment")
    private String typeOfPayment;

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

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public boolean isWithDriver() {
        return isWithDriver;
    }

    public void setWithDriver(boolean withDriver) {
        isWithDriver = withDriver;
    }

    public String getTypeOfVehicle() {
        return typeOfVehicle;
    }

    public void setTypeOfVehicle(String typeOfVehicle) {
        this.typeOfVehicle = typeOfVehicle;
    }

    public String getTypeOfPayment() {
        return typeOfPayment;
    }

    public void setTypeOfPayment(String typeOfPayment) {
        this.typeOfPayment = typeOfPayment;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }
}
