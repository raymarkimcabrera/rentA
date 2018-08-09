package com.skuld.user.rent_a.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;

public class PermissionRequestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_permission_request;
    }
}
