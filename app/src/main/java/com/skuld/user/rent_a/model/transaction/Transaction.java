package com.skuld.user.rent_a.model.transaction;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skuld.user.rent_a.model.offer.Offer;
import com.skuld.user.rent_a.model.reverse_geocoder.DisplayPosition;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Transaction implements Serializable {

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("userID")
    private String userID;

    @Expose
    @SerializedName("paymentID")
    private String paymentID;

    @Expose
    @SerializedName("destinationLocation")
    private DisplayPosition destinationLocation;

    @Expose
    @SerializedName("offerAccepted")
    private Offer offerAccepted;

    @Expose
    @SerializedName("pickupLocation")
    private DisplayPosition pickupLocation;

    @Expose
    @SerializedName("conversationID")
    private String conversationID;

    @Expose
    @SerializedName("startDate")
    private Date startDate;

    @Expose
    @SerializedName("endDate")
    private Date endDate;

    @Expose
    @SerializedName("rating")
    private int rating;

    @Expose
    @SerializedName("createdDate")
    @ServerTimestamp
    private Date createdDate;

    @Expose
    @SerializedName("passengers")
    private int passengers;

    @Expose
    @SerializedName("driverSpecifications")
    private String driverSpecifications;

    @Expose
    @SerializedName("typeOfVehicle")
    private String typeOfVehicle;

    @Expose
    @SerializedName("typeOfService")
    private String typeOfService;

    @Expose
    @SerializedName("typeOfPayment")
    private String typeOfPayment;

    @Expose
    @SerializedName("remarks")
    private String remarks;

    @Expose
    @SerializedName("pickUpAddress")
    private String pickUpAddress;

    @Expose
    @SerializedName("destinationAddress")
    private String destinationAddress;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("offerList")
    private List<Offer> offerList;

    public Offer getOfferAccepted() {
        return offerAccepted;
    }

    public void setOfferAccepted(Offer offerAccepted) {
        this.offerAccepted = offerAccepted;
    }

    public List<Offer> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<Offer> offerList) {
        this.offerList = offerList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public DisplayPosition getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(DisplayPosition destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public DisplayPosition getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(DisplayPosition pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public String getDriverSpecifications() {
        return driverSpecifications;
    }

    public void setDriverSpecifications(String driverSpecifications) {
        this.driverSpecifications = driverSpecifications;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
