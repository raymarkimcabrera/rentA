package com.skuld.user.rent_a;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.skuld.user.rent_a.activity.DashboardActivity;
import com.skuld.user.rent_a.views.MapsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity{

    protected Context mContext;

    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());

        ButterKnife.bind(this);

        mContext = this;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    protected abstract int setLayoutResourceID();

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    protected void checkForPermissions(int requestCode, String... permissions) {
        List<String> permissionsNeeded = new ArrayList<>();

        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);
            }
        }

        if (permissionsNeeded.size() > 0) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), requestCode);
            return;
        }

        permissionGranted(requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (isNeverClicked(permissions)) {
            permissionDenied("NEVER ASK AGAIN");
        }

        if (grantResults.length > 1) {
            // Handle multiple permission request
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    permissionDenied("Some Permission is Denied");
                    return;
                }
            }
            permissionGranted(requestCode);
        } else {
            // Handle single permission request
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionGranted(requestCode);
            } else {
                permissionDenied("Permission Denied");
            }
        }
    }

    protected boolean isNeverClicked(@NonNull String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                //denied
            } else {
                if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                    //allowed
                } else {
                    //set to never ask again
                    Log.e("set to never ask again", permission);
                    return true;
                }
            }
        }
        return false;
    }

    protected void permissionGranted(int requestCode) {
    }

    protected void permissionDenied(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
