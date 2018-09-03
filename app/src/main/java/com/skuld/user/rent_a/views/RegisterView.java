package com.skuld.user.rent_a.views;

import com.google.firebase.firestore.DocumentReference;

public interface RegisterView {

    void onRegisterSuccess(DocumentReference documentReference);

    void onRegisterFailed(String message);
}
