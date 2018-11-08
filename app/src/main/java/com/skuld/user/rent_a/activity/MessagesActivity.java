package com.skuld.user.rent_a.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.model.conversation.Message;
import com.skuld.user.rent_a.model.conversation.MessageList;
import com.skuld.user.rent_a.model.user.User;
import com.skuld.user.rent_a.presenter.MessagePresenter;
import com.skuld.user.rent_a.presenter.UsersPresenter;
import com.skuld.user.rent_a.utils.Preferences;
import com.skuld.user.rent_a.views.MessageListView;
import com.skuld.user.rent_a.views.UsersView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class MessagesActivity extends BaseActivity implements MessageListView, UsersView {

    @BindView(R.id.messagesLinearLayout)
    LinearLayout mMessagesLinearLayout;

    @BindView(R.id.textMessageEditText)
    EditText mTextMessageEditText;

    private MessageList mMessageList;
    private MessagePresenter mMessagePresenter;
    private UsersPresenter mUsersPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        initPresenter();

        getArgs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMessagePresenter.getConversation(mMessageList.getId());
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void initPresenter() {
        mMessagePresenter = new MessagePresenter(mContext, this);
        mUsersPresenter = new UsersPresenter(mContext, this);
    }

    public static Intent newIntent(Context context, MessageList messageList) {
        Intent intent = new Intent(context, MessagesActivity.class);
        intent.putExtra("THREAD", messageList);
        return intent;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_messages;
    }

    private void getArgs() {
        mMessageList = (MessageList) getIntent().getSerializableExtra("THREAD");
    }

    private void initUi() {
        mMessagesLinearLayout.removeAllViews();
        PrettyTime prettyTime = new PrettyTime();
        for (Message message : mMessageList.getThread()) {
            View driverMessageView = LayoutInflater.from(mContext).inflate(R.layout.list_item_driver_message, null);
            View userMessageView = LayoutInflater.from(mContext).inflate(R.layout.list_item_user_message, null);

            if (!message.getContent().isEmpty()) {

                View messageView;
                if (message.getSenderID().equals(Preferences.getString(mContext, Preferences.USER_ID)))
                    messageView = userMessageView;
                else
                    messageView = driverMessageView;


                TextView contentTextView = (TextView) messageView.findViewById(R.id.contentTextView);
                TextView createdAtTextView = (TextView) messageView.findViewById(R.id.createdAtTextView);
                ImageView avatarImageView = (ImageView) messageView.findViewById(R.id.avatarImageView);

                contentTextView.setText(message.getContent());
                createdAtTextView.setText(prettyTime.format(message.getCreatedAt()));

                mMessagesLinearLayout.addView(messageView);
                mMessagesLinearLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMessagesLinearLayout.getChildAt(mMessagesLinearLayout.getChildCount() -1 ).requestFocus();
                    }
                }, 1000);
            }

        }

    }

    @Override
    public void onGetConversationSuccess(MessageList messageLists) {
        initUi();
    }

    @Override
    public void onGetConversationError() {

    }

    @Override
    public void onNoConversation() {

    }

    @Override
    public void onMessageSent() {
        initUi();
        mTextMessageEditText.setText("");
    }

    @Override
    public void onMessageNotSent() {

    }

    @OnClick(R.id.sendImageView)
    void onClick() {
        mUsersPresenter.getUserProfile(Preferences.getString(mContext, Preferences.USER_ID));
    }

    @Override
    public void onGetUserSuccess(User user) {
        String message = mTextMessageEditText.getText().toString();
        Date currentDate = new Date(System.currentTimeMillis());
        String userId = Preferences.getString(mContext, Preferences.USER_ID);

        Message messageBody = new Message();

        messageBody.setContent(message);
        messageBody.setSenderID(userId);
        messageBody.setCreatedAt(currentDate);
        messageBody.setSenderName(user.getFirstName() +" "+ user.getLastName());

        mMessagePresenter.sendMessage(mMessageList, messageBody);

    }

    @Override
    public void onGetUserError() {
        Toast.makeText(mContext, "Failed to connect to server. Please try again.", Toast.LENGTH_SHORT).show();
    }
}
