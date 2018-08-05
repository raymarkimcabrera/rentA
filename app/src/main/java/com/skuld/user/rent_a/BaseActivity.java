package com.skuld.user.rent_a;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public abstract class BaseActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());

        mContext = this;
    }

    protected abstract int setLayoutResourceID();

}
