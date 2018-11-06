package com.skuld.user.rent_a.views;

import com.skuld.user.rent_a.model.driver.Driver;

public interface DriverView {

    void onGetDriverProfileSuccess(Driver driver);

    void onGetDriverProfileError();
}
