package com.skuld.user.rent_a.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;

public class SplashScreenActivity extends BaseActivity {


    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
            }
        }, 2000);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_splash_screen;
    }


}
