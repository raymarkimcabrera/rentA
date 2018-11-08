package com.skuld.user.rent_a.views;

import com.google.firebase.firestore.DocumentReference;

public interface RegisterView {

    void onRegisterSuccess();

    void onRegisterFailed(String message);
}
