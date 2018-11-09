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

    @Expose
    @SerializedName("userName")
    private String userName;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("contactNumber")
    private String contactNumber;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("imageUrl")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl == null ? "" : imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
