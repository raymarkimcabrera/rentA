package com.skuld.user.rent_a.views;

import com.skuld.user.rent_a.model.car.Car;

import java.util.ArrayList;
import java.util.List;

public interface OffersView {

    void onGetOffersSuccess(List<Car> carList);

    void onNoOffers();

    void onGetOffersError();
}
