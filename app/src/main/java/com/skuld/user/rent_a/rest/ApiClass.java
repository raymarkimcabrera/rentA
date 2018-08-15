package com.skuld.user.rent_a.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.model.SuggestionList;
import com.skuld.user.rent_a.model.SuggestionsItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClass implements Callback<SuggestionList> {

    public void start(String appId, String appToken, String query) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://autocomplete.geocoder.api.here.com/6.2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<SuggestionList> suggestionsItemCall = apiInterface.getSuggestions(appId,
                appToken,
                query);

        suggestionsItemCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<SuggestionList> call, Response<SuggestionList> response) {
        Gson gson = new Gson();
        if(response.isSuccessful()) {
            SuggestionList changesList =  (SuggestionList) response.body();
            System.out.print("onResponse" + gson.toJson(changesList));
            Log.e("GET", "onSuccess: " + changesList );
        } else {
            try {
                Log.e("ERROR", "onResponse: " + response.errorBody().string() );
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
    public void onFailure(Call<SuggestionList> call, Throwable t) {
        t.printStackTrace();
        Log.e("GET", "onFailure: " + t.getMessage() );
    }
}
