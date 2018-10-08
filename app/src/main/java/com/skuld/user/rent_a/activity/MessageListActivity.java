package com.skuld.user.rent_a.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.adapter.MessageRecyclerViewAdapter;
import com.skuld.user.rent_a.model.conversation.MessageList;
import com.skuld.user.rent_a.presenter.MessagePresenter;
import com.skuld.user.rent_a.utils.Preferences;
import com.skuld.user.rent_a.views.MessageListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MessageListActivity extends BaseActivity implements MessageListView {

    @BindView(R.id.messageRecyclerView)
    RecyclerView mMessageRecyclerView;

    private MessagePresenter mMessagePresenter;
    private List<MessageList> mMessageLists;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MessageListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        initPresenter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initUi();
        mMessagePresenter.getConversationList(Preferences.getString(mContext, Preferences.USER_ID));
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_message_list;
    }

    @Override
    public void onGetConversationSuccess(MessageList messageList) {

        mMessageLists.add(messageList);

        for (MessageList messageList1: mMessageLists){
            Log.i("onGetConversation", "onGetConversationSuccess: " + messageList1.getId());
        }
        MessageRecyclerViewAdapter messageRecyclerViewAdapter = new MessageRecyclerViewAdapter(mContext, mMessageLists);

        mMessageRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mMessageRecyclerView.setLayoutManager(llm);
        mMessageRecyclerView.setAdapter(messageRecyclerViewAdapter);
        messageRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetConversationError() {

    }

    private void initPresenter() {
        mMessagePresenter = new MessagePresenter(mContext, this);
    }

    private void initUi() {
        mMessageLists = new ArrayList<>();
    }

}
