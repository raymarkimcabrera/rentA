package com.skuld.user.rent_a.views;

import com.skuld.user.rent_a.model.car.Car;
import com.skuld.user.rent_a.model.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public interface OffersView {

    void onGetOffersSuccess(List<Car> carList);

    void onNoOffers();

    void onGetOffersError();

    void onGetReviewsSuccess(List<Transaction> transactions);

    void onGetReviewsError();

}
