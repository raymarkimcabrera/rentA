package com.skuld.user.rent_a.presenter;

import android.content.Context;

import com.skuld.user.rent_a.activity.AutoCompleteKeyboardActivity;
import com.skuld.user.rent_a.model.autocomplete.SuggestionList;
import com.skuld.user.rent_a.model.reverse_geocoder.ReverseGeocoderResponse;
import com.skuld.user.rent_a.rest.ApiInterface;
import com.skuld.user.rent_a.views.LocationDetailsView;
import com.skuld.user.rent_a.views.ReverseGeoCoderView;
import com.skuld.user.rent_a.views.SuggestionsView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutoCompletePresenter extends BasePresenter {

    private ApiInterface mApiInterface;
    private SuggestionsView mSuggestionView;
    private LocationDetailsView mLocationDetailsView;
    private Context mContext;

    public AutoCompletePresenter(Context mContext , ApiInterface mApiInterface, SuggestionsView mSuggestionView) {
        this.mApiInterface = mApiInterface;
        this.mSuggestionView = mSuggestionView;
        this.mContext = mContext;
    }

    public AutoCompletePresenter(Context mContextApiInterface, ApiInterface mApiInterface, LocationDetailsView locationDetailsView) {
        this.mApiInterface = mApiInterface;
        this.mLocationDetailsView = locationDetailsView;
        this.mContext = mContext;
    }

    public void getSuggestions(String query){
        Call<SuggestionList> suggestionsItemCall = mApiInterface.getSuggestions(query);

        suggestionsItemCall.enqueue(new Callback<SuggestionList>() {
            @Override
            public void onResponse(Call<SuggestionList> call, Response<SuggestionList> response) {
                mSuggestionView.onSuggestionSuccess(response.body().getSuggestionsItemList());
            }

            @Override
            public void onFailure(Call<SuggestionList> call, Throwable t) {

            }
        });
    }

    public void getLocationDetailsByID(String locationID){
        Call<ReverseGeocoderResponse> locationDetails = mApiInterface.getLocationDetailsByID(locationID);

        locationDetails.enqueue(new Callback<ReverseGeocoderResponse>() {
            @Override
            public void onResponse(Call<ReverseGeocoderResponse> call, Response<ReverseGeocoderResponse> response) {
                mLocationDetailsView.onLocationDetailsSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ReverseGeocoderResponse> call, Throwable t) {

            }
        });
    }
}
