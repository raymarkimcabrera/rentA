package com.skuld.user.rent_a.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;

import butterknife.BindView;
import butterknife.OnClick;

public class PermissionRequestActivity extends BaseActivity {

    public static final int REQUEST_CODE_PERMISSION_STORAGE = 101;
    public static final int REQUEST_CODE_PERMISSION_LOCATION = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PermissionRequestActivity.class);
        return intent;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_permission_request;
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            checkForPermissions(REQUEST_CODE_PERMISSION_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            checkForPermissions(REQUEST_CODE_PERMISSION_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void permissionGranted(int requestCode) {
        startActivity(DashboardActivity.newIntent(mContext));
    }

    @Override
    protected void permissionDenied(String message) {

    }

    @OnClick(R.id.permissionRequestButton)
    void onClick() {
        checkPermissions();
    }
}
