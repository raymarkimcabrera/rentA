package com.skuld.user.rent_a;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skuld.user.rent_a.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public abstract class BaseActivity extends AppCompatActivity{ 

    public final static String ALL_OUT = "ALL_OUT";
    public final static String SEPERATE_PAYMENTS = "SEPERATE_PAYMENTS";
    public final static String FULLY_PAID = "FULLY_PAID";
    public final static String PARTIALLY_PAID = "PARTIALLY_PAID";
    public final static String PENDING = "PENDING";
    public final static String ONE_WAY = "ONE_WAY";
    public final static String ROUND_TRIP = "ROUND_TRIP";
    public final static String WITH_DRIVER = "WITH_DRIVER";
    public final static String WITHOUT_DRIVER = "WITHOUT_DRIVER";

    protected Context mContext;

    protected Toolbar mToolbar;

    protected ApiInterface mApiInterface;

    protected FirebaseFirestore mDatabase;

    private HttpLoggingInterceptor interceptor;
    private OkHttpClient.Builder okHttpClientBuilder;
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

        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(interceptor);
    }

    private Gson getGson() {
        return new GsonBuilder()
                .setLenient()
                .create();

    }
//    API Functions


    public ApiInterface autoCompleteAPI(){
         Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://autocomplete.geocoder.api.here.com/" + getString(R.string.here_api_version) + "/")
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                 .client(okHttpClientBuilder.build())
                .build();
        return retrofit.create(ApiInterface.class);
    }

    public ApiInterface getLocationDetailsAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reverse.geocoder.api.here.com/" + getString(R.string.here_api_version) + "/")
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(okHttpClientBuilder.build())
                .build();

        return retrofit.create(ApiInterface.class);
    }

    public ApiInterface getLocationDetailsByIDAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://geocoder.api.here.com/" + getString(R.string.here_api_version) + "/")
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(okHttpClientBuilder.build())
                .build();

        return retrofit.create(ApiInterface.class);
    }

//    END API Functions
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

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
