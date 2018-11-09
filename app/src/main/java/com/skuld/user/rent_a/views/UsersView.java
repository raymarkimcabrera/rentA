package com.skuld.user.rent_a.views;

import com.skuld.user.rent_a.model.driver.Driver;
import com.skuld.user.rent_a.model.user.User;

public interface UsersView {

    void onGetUserSuccess(User user);

    void onGetUserError();

    void onUserUpdateSuccess();

    void onUserUpdateError();

    void onGetDriverProfileSuccess(Driver driver);

    void onGetDriverProfileError();
}
