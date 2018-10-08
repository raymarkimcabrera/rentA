package com.skuld.user.rent_a.views;

import com.skuld.user.rent_a.model.conversation.MessageList;

import java.util.List;

public interface MessageListView {

    void onGetConversationSuccess(MessageList messageLists);
    void onGetConversationError();
}
