package com.skuld.user.rent_a.views;

import com.skuld.user.rent_a.model.reverse_geocoder.ReverseGeocoderResponse;

public interface LocationDetailsView {

    void onLocationDetailsSuccess(ReverseGeocoderResponse response);
}
