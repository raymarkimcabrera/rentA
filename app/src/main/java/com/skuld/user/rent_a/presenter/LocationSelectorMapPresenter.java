package com.skuld.user.rent_a.presenter;

import android.content.Context;
import android.util.Log;

import com.skuld.user.rent_a.model.reverse_geocoder.ReverseGeocoderResponse;
import com.skuld.user.rent_a.rest.ApiInterface;
import com.skuld.user.rent_a.views.LocationDetailsView;
import com.skuld.user.rent_a.views.ReverseGeoCoderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationSelectorMapPresenter extends BasePresenter {
    private static final String TAG = LocationSelectorMapPresenter.class.getSimpleName();
    private static final String REVERSE_GEOCODER_CALL_FLAG = "REVERSE_GEOCODER_CALL_FLAG";
    private static final String GET_LOCATION_FLAG = "GET_LOCATION_FLAG";

    private ApiInterface mApiInterface;
    private ReverseGeoCoderView mReverseGeoCoderView;
    private LocationDetailsView mLocationDetailsView;
    private Context mContext;

    public LocationSelectorMapPresenter(Context context, ApiInterface apiInterface, ReverseGeoCoderView reverseGeoCoderView) {
        this.mApiInterface = apiInterface;
        this.mReverseGeoCoderView = reverseGeoCoderView;
        this.mContext = context;
    }

    public LocationSelectorMapPresenter(Context context, ApiInterface apiInterface, LocationDetailsView locationDetailsView) {
        this.mApiInterface = apiInterface;
        this.mLocationDetailsView = locationDetailsView;
        this.mContext = context;
    }

    public void reverseGeocoderCall(String query, String mode, int maxResults) {
        showProgressDialog(mContext);
        Call<ReverseGeocoderResponse> reverseGeocoderCall = mApiInterface.getLocationDetails(query,
                mode,
                maxResults);

        reverseGeocoderCall.enqueue(new Callback<ReverseGeocoderResponse>() {
            @Override
            public void onResponse(Call<ReverseGeocoderResponse> call, Response<ReverseGeocoderResponse> response) {
                hideProgressDialog();
                mReverseGeoCoderView.onReverseGeoCoderCallSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ReverseGeocoderResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });

    }

    public void getLocationDetailsByID(String locationID) {
        showProgressDialog(mContext);
        Call<ReverseGeocoderResponse> locationDetails = mApiInterface.getLocationDetailsByID(locationID);

        locationDetails.enqueue(new Callback<ReverseGeocoderResponse>() {
            @Override
            public void onResponse(Call<ReverseGeocoderResponse> call, Response<ReverseGeocoderResponse> response) {
                hideProgressDialog();
                mLocationDetailsView.onLocationDetailsSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ReverseGeocoderResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }

}
