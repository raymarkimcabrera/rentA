package com.skuld.user.rent_a.model.conversation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MessageList implements Serializable{

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("thread")
    private List<Message> thread;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Message> getThread() {
        return thread;
    }

    public void setThread(List<Message> thread) {
        this.thread = thread;
    }

    public Message getLastMessage(){
        if (thread.size() > 0){
            int size = thread.size();
            return thread.get(size);
        }
        return null;
    }
}
