package com.skuld.user.rent_a.rest;


import com.google.gson.JsonObject;
import com.skuld.user.rent_a.model.LocationList;
import com.skuld.user.rent_a.model.LocationList;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiInterface {

    @GET("/suggest.json")
    Observable<LocationList> getSuggestions(@Query("app_id") String appID,
                                            @Query("app_code") String appToken,
                                            @Query("query") String query);


}
