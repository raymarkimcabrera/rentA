package com.skuld.user.rent_a.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapView;
import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.BuildConfig;
import com.skuld.user.rent_a.R;

import butterknife.BindView;

public class DashboardActivity extends BaseActivity implements OnEngineInitListener{

    public static final int REQUEST_CODE_PERMISSION_STORAGE = 101;
    public static final int REQUEST_CODE_PERMISSION_LOCATION = 102;

    @BindView(R.id.navigationView)
    NavigationView mNavigationView;

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.findVehicleButton)
    Button mFindVehicleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();

        initNavigationMenu();

        initializeMaps();


    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, DashboardActivity.class);
        return intent;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_dashboard;
    }


    private void initialize() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setTitle(R.string.app_name);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initNavigationMenu() {
        mNavigationView.inflateMenu(R.menu.drawer_logged_in);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDrawerLayout.closeDrawer(GravityCompat.START);

                switch (menuItem.getItemId()) {
                    case R.id.menuItemHome:
                        break;

                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        Menu menu = mNavigationView.getMenu();
        MenuItem appVersion = menu.findItem(R.id.menuAppVersion);
        appVersion.setTitle("Version " + BuildConfig.VERSION_NAME);

    }

    private void initializeMaps(){

        // initialize the Map Fragment and
        // retrieve the map that is associated to the fragment
        checkForPermissions(REQUEST_CODE_PERMISSION_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION);
        checkForPermissions(REQUEST_CODE_PERMISSION_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE);


        final MapFragment mMapFragment = (MapFragment)
                getFragmentManager().findFragmentById(R.id.mapfragment);

        mMapFragment.init(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        }
    }

    @Override
    public void onEngineInitializationCompleted(Error error) {
        final MapFragment mMapFragment = (MapFragment)
                getFragmentManager().findFragmentById(R.id.mapfragment);

        if (error == OnEngineInitListener.Error.NONE) {
            // now the map is ready to be used
            Map mMap = mMapFragment.getMap();
            // ...
            mMap.setCenter(new GeoCoordinate(51.51, -0.11),
                    Map.Animation.NONE);

// Get the current center of the Map
            GeoCoordinate coordinate = mMap.getCenter();

            // Get the maximum,minimum zoom level.
            double maxZoom = mMap.getMaxZoomLevel();
            double minZoom = mMap.getMinZoomLevel();

// Set the zoom level to the median (10).
            mMap.setZoomLevel((maxZoom + minZoom) / 2);

// Get the zoom level back
            double zoom = mMap.getZoomLevel();
        } else {
            Log.i("Map Initialization", "onEngineInitializationCompleted: " + error);
            checkForPermissions(REQUEST_CODE_PERMISSION_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            checkForPermissions(REQUEST_CODE_PERMISSION_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE);
            mMapFragment.init(this);
        }
    }
}
