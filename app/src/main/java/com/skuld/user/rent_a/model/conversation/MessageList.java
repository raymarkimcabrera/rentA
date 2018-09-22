package com.skuld.user.rent_a.model.conversation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MessageList implements Serializable{

    @Expose
    @SerializedName("thread")
    private List<Message> thread;

    public List<Message> getThread() {
        return thread;
    }

    public void setThread(List<Message> thread) {
        this.thread = thread;
    }
}
