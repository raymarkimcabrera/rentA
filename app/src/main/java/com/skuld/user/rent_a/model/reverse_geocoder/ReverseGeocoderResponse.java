package com.skuld.user.rent_a.model.reverse_geocoder;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReverseGeocoderResponse implements Serializable {

    @SerializedName("Response")
    private ReverseGeocoder reverseGeocoder;


    public ReverseGeocoder getReverseGeocoder() {
        return reverseGeocoder;
    }

    public void setReverseGeocoder(ReverseGeocoder reverseGeocoder) {
        this.reverseGeocoder = reverseGeocoder;
    }
}
