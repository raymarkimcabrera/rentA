package com.skuld.user.rent_a.rest;



import com.skuld.user.rent_a.model.autocomplete.SuggestionList;
import com.skuld.user.rent_a.model.reverse_geocoder.ReverseGeocoder;
import com.skuld.user.rent_a.model.reverse_geocoder.ReverseGeocoderResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    String appID = "y5fHswCIlivtUId6Ehsr";
    String appToken = "USe_2O9CqyXBcEmltKQOtA";
    String credentials = "?app_id="+ appID+"&app_code=" + appToken;

    @GET("suggest.json" + credentials)
    Call<SuggestionList> getSuggestions(@Query("query") String query);

    @GET("reversegeocode.json" + credentials)
    Call<ReverseGeocoderResponse> getLocationDetails(@Query("prox") String query,
                                                     @Query("mode") String mode,
                                                     @Query("maxresults") int maxResults);

}
