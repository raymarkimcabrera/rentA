package com.skuld.user.rent_a.views;

import com.google.firebase.auth.FirebaseUser;

public interface GoogleLoginView {

    void onGmailLoginSuccess(FirebaseUser user);

    void onGmailLoginError(String message);
}
