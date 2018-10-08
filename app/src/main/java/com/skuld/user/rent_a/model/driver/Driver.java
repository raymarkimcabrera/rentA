package com.skuld.user.rent_a.model.driver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skuld.user.rent_a.model.conversation.Message;

import java.io.Serializable;

public class Driver implements Serializable {

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("address")
    private String address;

    @Expose
    @SerializedName("age")
    private String age;

    @Expose
    @SerializedName("firstName")
    private String firstName;

    @Expose
    @SerializedName("middleName")
    private String middleName;

    @Expose
    @SerializedName("lastName")
    private String lastName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
