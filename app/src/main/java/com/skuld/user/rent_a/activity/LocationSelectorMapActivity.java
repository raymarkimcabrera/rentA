package com.skuld.user.rent_a.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapMarker;
import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.model.reverse_geocoder.Location;
import com.skuld.user.rent_a.model.reverse_geocoder.ReverseGeocoderResponse;
import com.skuld.user.rent_a.presenter.LocationSelectorMapPresenter;
import com.skuld.user.rent_a.views.LocationDetailsView;
import com.skuld.user.rent_a.views.ReverseGeoCoderView;

import butterknife.OnClick;

public class LocationSelectorMapActivity extends BaseActivity implements OnEngineInitListener, ReverseGeoCoderView, LocationDetailsView {
    private static final String TAG = LocationSelectorMapActivity.class.getSimpleName();
    private static final int COVERAGE_RADIUS = 100;
    private static final int MAX_RESULTS = 1;
    private static final String MODE = "retrieveAddresses";

    private int mEditTextID;
    private MapFragment mMapFragment;
    private Map mMap;
    private MapMarker mMapMarker;
    private Context mContext;

    private LocationSelectorMapPresenter mLocationSelecctorMapPresenter;

    public static Intent newIntent(Context context, int editTextID) {
        Intent intent = new Intent(context, LocationSelectorMapActivity.class);
        intent.putExtra(AutoCompleteKeyboardActivity.RESULT_EDIT_TEXT_ID, editTextID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        mEditTextID = bundle.getInt(AutoCompleteKeyboardActivity.RESULT_EDIT_TEXT_ID);
        mContext = this;

        initializeMaps();

        initializeAPI();

        initializePresenter();
    }

    private void initializePresenter() {
        mLocationSelecctorMapPresenter = new LocationSelectorMapPresenter(mContext, mApiInterface, (ReverseGeoCoderView) this);
    }

    private void initializeAPI() {
        mApiInterface = getLocationDetailsAPI();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_location_selector_map;
    }

    @Override
    public void onEngineInitializationCompleted(Error error) {

        if (error == OnEngineInitListener.Error.NONE) {
            // now the map is ready to be used

            mMap = mMapFragment.getMap();

        } else {
            Log.i("Map Initialization", "onEngineInitializationCompleted: " + error);
        }
    }

    private void initializeMaps() {
        mMapFragment = (MapFragment)
                getFragmentManager().findFragmentById(R.id.mapfragment);

        mMapFragment.init(this);

    }

    @OnClick(R.id.selectLocationButton)
    void OnClick() {

        mLocationSelecctorMapPresenter.reverseGeocoderCall(mMap.getCenter().getLatitude() +
                        "," + mMap.getCenter().getLongitude() +
                        "," + COVERAGE_RADIUS,
                MODE,
                MAX_RESULTS);
    }

    @Override
    public void onReverseGeoCoderCallSuccess(ReverseGeocoderResponse response) {
        Location location = response.getLocationDetails();
        mApiInterface = getLocationDetailsByIDAPI();
        mLocationSelecctorMapPresenter = new LocationSelectorMapPresenter(mContext, mApiInterface, (LocationDetailsView) this);
        mLocationSelecctorMapPresenter.getLocationDetailsByID(location.getLocationId());
    }

    @Override
    public void onLocationDetailsSuccess(ReverseGeocoderResponse response) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(AutoCompleteKeyboardActivity.RESULT_EDIT_TEXT_ID, mEditTextID);
        returnIntent.putExtra(DashboardActivity.LOCATION, response.getLocationDetails());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
