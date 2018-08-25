package com.skuld.user.rent_a.presenter;

import android.content.Context;

import com.skuld.user.rent_a.model.reverse_geocoder.ReverseGeocoderResponse;
import com.skuld.user.rent_a.rest.ApiInterface;
import com.skuld.user.rent_a.views.LocationDetailsView;
import com.skuld.user.rent_a.views.ReverseGeoCoderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationSelectorMapPresenter extends BasePresenter implements Callback<ReverseGeocoderResponse> {
    private static final String REVERSE_GEOCODER_CALL_FLAG = "REVERSE_GEOCODER_CALL_FLAG";
    private static final String GET_LOCATION_FLAG = "GET_LOCATION_FLAG";

    private String mFlag;
    private ApiInterface mApiInterface;
    private ReverseGeoCoderView mReverseGeoCoderView;
    private LocationDetailsView mLocationDetailsView;
    private Context mContext;

    public LocationSelectorMapPresenter(Context context, ApiInterface apiInterface, ReverseGeoCoderView reverseGeoCoderView) {
        this.mApiInterface = apiInterface;
        this.mReverseGeoCoderView = reverseGeoCoderView;
        this.mContext = context;
    }

    public LocationSelectorMapPresenter(Context context, ApiInterface apiInterface, LocationDetailsView locationDetailsView){
        this.mApiInterface = apiInterface;
        this.mLocationDetailsView = locationDetailsView;
        this.mContext = context;
    }

    public void reverseGeocoderCall(String query, String mode, int maxResults) {
        mFlag = REVERSE_GEOCODER_CALL_FLAG;
        showProgressDialog(mContext);
        Call<ReverseGeocoderResponse> reverseGeocoderCall = mApiInterface.getLocationDetails(query,
                mode,
                maxResults);

        reverseGeocoderCall.enqueue(this);

    }

    public void getLocationID(String locationID){
        mFlag = GET_LOCATION_FLAG;
        showProgressDialog(mContext);
        Call<ReverseGeocoderResponse> locationDetails = mApiInterface.getLocationDetailsByID(locationID);

        locationDetails.enqueue(this);
    }

    @Override
    public void onResponse(Call<ReverseGeocoderResponse> call, Response<ReverseGeocoderResponse> response) {
        hideProgressDialog();
        if (mFlag.equals(REVERSE_GEOCODER_CALL_FLAG)) {
            mReverseGeoCoderView.onReverseGeoCoderCallSuccess(response.body());
        } else if (mFlag.equals(GET_LOCATION_FLAG)){
            mLocationDetailsView.onLocationDetailsSuccess(response.body());
        }
    }

    @Override
    public void onFailure(Call<ReverseGeocoderResponse> call, Throwable t) {

    }

}
