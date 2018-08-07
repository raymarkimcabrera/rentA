package com.skuld.user.rent_a.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;

import butterknife.BindView;

public class MessagesActivity extends BaseActivity {

    @BindView(R.id.messagesLinearLayout)
    LinearLayout mMessagesLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_messages;
    }
}
