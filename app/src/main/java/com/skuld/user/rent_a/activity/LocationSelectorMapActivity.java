package com.skuld.user.rent_a.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.ViewObject;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;
import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;

import java.io.IOException;
import java.util.List;

public class LocationSelectorMapActivity extends BaseActivity implements OnEngineInitListener, MapMarker.OnDragListener {

    private MapFragment mMapFragment;
    private Map mMap;
    private MapMarker mMapMarker;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LocationSelectorMapActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeMaps();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_location_selector_map;
    }

    @Override
    public void onEngineInitializationCompleted(Error error) {

        com.here.android.mpa.common.Image myImage =
                new com.here.android.mpa.common.Image();

        try {
            myImage.setImageResource(R.drawable.ic_location);
        } catch (IOException e) {
            finish();
        }


        if (error == OnEngineInitListener.Error.NONE) {
            // now the map is ready to be used
            mMapMarker = new MapMarker(new GeoCoordinate(14.5979292, 121.0085669), myImage);
            mMapMarker.setDraggable(true);

            mMap = mMapFragment.getMap();
            mMap.addMapObject(mMapMarker);

        } else {
            Log.i("Map Initialization", "onEngineInitializationCompleted: " + error);
        }
    }

    private void initializeMaps() {
        mMapFragment = (MapFragment)
                getFragmentManager().findFragmentById(R.id.mapfragment);

        mMapFragment.init(this);

    }

    @Override
    public void onMarkerDrag(MapMarker mapMarker) {

    }

    @Override
    public void onMarkerDragEnd(MapMarker mapMarker) {
        Toast.makeText(mContext, "" + mapMarker.getCoordinate(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMarkerDragStart(MapMarker mapMarker) {

    }
}
