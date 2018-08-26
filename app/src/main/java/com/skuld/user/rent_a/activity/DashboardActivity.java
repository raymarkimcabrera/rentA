package com.skuld.user.rent_a.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapMarker;
import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.BuildConfig;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.model.reverse_geocoder.Address;
import com.skuld.user.rent_a.model.reverse_geocoder.DisplayPosition;
import com.skuld.user.rent_a.model.reverse_geocoder.Location;

import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.BindView;

public class DashboardActivity extends BaseActivity implements OnEngineInitListener, PositioningManager.OnPositionChangedListener {

    private static final String TAG = DashboardActivity.class.getSimpleName();

    public static final int REQUEST_CODE_PERMISSION_STORAGE = 101;
    public static final int REQUEST_CODE_PERMISSION_LOCATION = 102;
    public static final String LOCATION = "LOCATION";

    public static int REQUEST_CODE_EDIT_TEXTFIELD = 1;
    public static int REQUEST_CODE_MAP_LOCATION_SELECT = 2;

    @BindView(R.id.navigationView)
    NavigationView mNavigationView;

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.findVehicleButton)
    Button mFindVehicleButton;

    @BindView(R.id.destinationTextView)
    TextView mDestinationEditText;

    @BindView(R.id.pickUpTextView)
    TextView mPickUpeditText;

    private Map mMap;
    private boolean paused;
    public PositioningManager posManager;
    private Location mDestinationLocation;
    private Location mPickUpLocation;
    private MapMarker mDestinationMarker;
    private MapMarker mPickUpMarker;
    private GeoCoordinate mDestinationGeoCoordinate;
    private GeoCoordinate mPickUpGeoCoordinate;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, DashboardActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();

        initNavigationMenu();

        initViews();

        initializeMaps();

        posManager = PositioningManager.getInstance();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected void onDestroy() {
        if (posManager != null) {
            // Cleanup
            posManager.removeListener(
                    this);
        }
        mMap = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        statusCheck();
        paused = false;
        if (posManager != null) {
            getLocation();
        }
    }

    @Override
    protected void onPause() {
        if (posManager != null) {
            posManager.stop();
        }
        super.onPause();
        paused = true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                else if (!mDrawerLayout.isDrawerVisible(GravityCompat.START))
                    mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_EDIT_TEXTFIELD) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();

                int editTextId = bundle.getInt(AutoCompleteKeyboardActivity.RESULT_EDIT_TEXT_ID);
                Location location = (Location) bundle.getSerializable(LOCATION);
                TextView textView = (TextView) findViewById(editTextId);
                com.here.android.mpa.common.Image displayImage = new com.here.android.mpa.common.Image();

                try {
                    displayImage.setImageResource(R.drawable.ic_location);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (editTextId == R.id.destinationTextView) {
                    if (mDestinationMarker != null)
                        mMap.removeMapObject(mDestinationMarker);

                    mDestinationLocation = location;
                    DisplayPosition destinationDisplayPosition = location.getDisplayPosition();
                    mDestinationGeoCoordinate = new GeoCoordinate(destinationDisplayPosition.getLatitude(), destinationDisplayPosition.getLongitude());
                    mDestinationMarker = new MapMarker(mDestinationGeoCoordinate, displayImage);
                    mMap.addMapObject(mDestinationMarker);

                } else if (editTextId == R.id.pickUpTextView) {

                    if (mPickUpMarker != null)
                        mMap.removeMapObject(mPickUpMarker);

                    mPickUpLocation = location;
                    DisplayPosition pickUpLocationDisplayPosition = mPickUpLocation.getDisplayPosition();
                    mPickUpGeoCoordinate = new GeoCoordinate(pickUpLocationDisplayPosition.getLatitude(), pickUpLocationDisplayPosition.getLongitude());
                    mPickUpMarker = new MapMarker(mPickUpGeoCoordinate, displayImage);
                    mMap.addMapObject(mPickUpMarker);
                }

                if (location != null) {
                    String address = location.getAddress().getFullAddress();
                    textView.setText(address);
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }


    private void initialize() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.img_menu);
            actionBar.setTitle(R.string.app_name);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initNavigationMenu() {
        mNavigationView.getMenu().clear();
        mNavigationView.inflateMenu(R.menu.drawer_logged_in);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDrawerLayout.closeDrawer(GravityCompat.START);

                switch (menuItem.getItemId()) {
                    case R.id.menuItemHome:
                        break;

                    case R.id.menuMessages:
                        break;

                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        Menu menu = mNavigationView.getMenu();
        MenuItem appVersion = menu.findItem(R.id.menuAppVersion);
        appVersion.setTitle("Version " + BuildConfig.VERSION_NAME);

    }

    private void initializeMaps() {

        // initialize the Map Fragment and
        // retrieve the map that is associated to the fragment
        checkForPermissions(REQUEST_CODE_PERMISSION_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION);
        checkForPermissions(REQUEST_CODE_PERMISSION_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE);


        final MapFragment mMapFragment = (MapFragment)
                getFragmentManager().findFragmentById(R.id.mapfragment);

        mMapFragment.init(this);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");
    }


    @Override
    public void onEngineInitializationCompleted(Error error) {
        final MapFragment mMapFragment = (MapFragment)
                getFragmentManager().findFragmentById(R.id.mapfragment);

        if (error == OnEngineInitListener.Error.NONE) {
            // now the map is ready to be used
            mMap = mMapFragment.getMap();

        } else {
            Log.i("Map Initialization", "onEngineInitializationCompleted: " + error);
        }
    }

    // Define positioning listener

    @Override
    public void onPositionUpdated(PositioningManager.LocationMethod locationMethod, GeoPosition geoPosition, boolean b) {
//        if (!paused) {
        Log.i(TAG, "onPositionUpdated: " + geoPosition.getCoordinate());
        if (mMap != null) {
            mMap.setCenter(geoPosition.getCoordinate(),
                    Map.Animation.NONE);
            setCenterAndZoom(geoPosition);
            mMap.getPositionIndicator().setVisible(true);
        }

        // Get the current center of the Map

//        }
    }

    public void onPositionFixChanged(PositioningManager.LocationMethod method,
                                     PositioningManager.LocationStatus status) {
    }

    private void setCenterAndZoom(GeoPosition geoPosition) {
        mMap.setZoomLevel(mMap.getMaxZoomLevel() - 2);
        Log.i(TAG, "setCenterAndZoom: " + mMap.getMaxZoomLevel());
    }

    private void getLocation() {
        posManager.getLocationStatus(PositioningManager.LocationMethod.NETWORK);

        posManager.start(
                PositioningManager.LocationMethod.NETWORK);

// Register positioning listener
        posManager.addListener(
                new WeakReference<PositioningManager.OnPositionChangedListener>(this));
    }

    private void initViews() {
        registerEditTextToShowAutoComplete(mPickUpeditText, false);
        registerEditTextToShowAutoComplete(mDestinationEditText, false);
    }

    public void registerEditTextToShowAutoComplete(final TextView textView, final boolean removeFocusAfter) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomKeyboardActivity(textView, removeFocusAfter);
            }
        });
    }

    private void openCustomKeyboardActivity(TextView textView, final boolean removeFocusAfter) {
        Intent intent = AutoCompleteKeyboardActivity.newIntent(mContext, textView.getId(), removeFocusAfter);
        startActivityForResult(intent, REQUEST_CODE_EDIT_TEXTFIELD);
    }

    private void getLocationDetailsFromLocationSelectorActivity() {

    }

}


