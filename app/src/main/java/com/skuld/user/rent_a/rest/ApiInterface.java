package com.skuld.user.rent_a.rest;



import com.google.gson.JsonObject;
import com.skuld.user.rent_a.model.LocationList;
import com.skuld.user.rent_a.model.LocationList;
import com.skuld.user.rent_a.model.SuggestionList;
import com.skuld.user.rent_a.model.SuggestionsItem;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("suggest.json")
    Call<SuggestionList> getSuggestions(@Query("app_id") String appID,
                                        @Query("app_code") String appToken,
                                        @Query("query") String query);


}
