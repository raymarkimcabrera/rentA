package com.skuld.user.rent_a.views;

import com.skuld.user.rent_a.model.user.User;

public interface UsersView {

    void onGetUserSuccess(User user);

    void onGetUserError();
}
