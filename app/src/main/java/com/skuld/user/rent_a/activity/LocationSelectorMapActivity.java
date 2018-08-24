package com.skuld.user.rent_a.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapMarker;
import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.model.reverse_geocoder.ReverseGeocoder;
import com.skuld.user.rent_a.model.reverse_geocoder.ReverseGeocoderResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationSelectorMapActivity extends BaseActivity implements OnEngineInitListener, Callback<ReverseGeocoderResponse>{

    private static final int COVERAGE_RADIUS = 100;
    private static final int MAX_RESULTS = 1;
    private static final String MODE = "retrieveAddresses";

    private MapFragment mMapFragment;
    private Map mMap;
    private MapMarker mMapMarker;
    private Context mContext;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LocationSelectorMapActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        initializeMaps();

        initializeAPI();
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
    void OnClick(){
        Log.e("LATLANG", "OnClick: " + mMap.getCenter().getLatitude() +
                "," + mMap.getCenter().getLongitude() +
                "," + COVERAGE_RADIUS );
        Call<ReverseGeocoderResponse> reverseGeocoderCall = mApiInterface.getLocationDetails(mMap.getCenter().getLatitude() +
                "," + mMap.getCenter().getLongitude() +
                "," + COVERAGE_RADIUS,
                MODE,
                MAX_RESULTS);

        reverseGeocoderCall.enqueue(LocationSelectorMapActivity.this);
    }

    @Override
    public void onResponse(Call<ReverseGeocoderResponse> call, Response<ReverseGeocoderResponse> response) {
        Gson gson = new Gson();
        if(response.isSuccessful()) {
            ReverseGeocoderResponse changesList =  response.body();
            System.out.print("onResponse" + gson.toJson(changesList));
            Log.e("GET", "onSuccess: " + changesList.getReverseGeocoder().toString() );
        } else {
            try {
                Log.e("ERROR", "onErrorBody: " + response.errorBody().string() );
                JSONObject jObjError = new JSONObject(response.errorBody().string());

                Log.e("GET", "onErrorBody: " + jObjError.get("message") );
                Log.e("GET", "onErrorBody: " + jObjError.get("code") );
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<ReverseGeocoderResponse> call, Throwable t) {

    }
}
