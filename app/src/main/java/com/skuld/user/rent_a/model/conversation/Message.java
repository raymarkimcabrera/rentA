package com.skuld.user.rent_a.model.conversation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Message implements Serializable {

    @Expose
    @SerializedName("content")
    private String content;

    @Expose
    @SerializedName("createdAt")
    private Date createdAt;

    @Expose
    @SerializedName("senderID")
    private String senderID;

    @Expose
    @SerializedName("senderName")
    private String senderName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
