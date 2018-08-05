package com.skuld.user.rent_a.activity;

import android.os.Bundle;

import com.facebook.CallbackManager;
import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;

public class LoginActivity extends BaseActivity {

    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login;
    }
}
