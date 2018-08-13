package com.skuld.user.rent_a.model;

import android.support.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Keep
public class LocationList implements Serializable {

    @SerializedName("suggestions")
    @Expose
    public List<Location> suggestions;

    public List<Location> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Location> suggestions) {
        this.suggestions = suggestions;
    }
}
