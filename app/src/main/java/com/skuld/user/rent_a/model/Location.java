package com.skuld.user.rent_a.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {

    @SerializedName("label")
    @Expose
    public String label;

    @SerializedName("language")
    @Expose
    public String language;

    @SerializedName("countryCode")
    @Expose
    public String countryCode;

    @SerializedName("locationId")
    @Expose
    public String locationId;

    @SerializedName("address")
    @Expose
    public String address;

    @SerializedName("label")
    @Expose
    public String matchLevel;

}
