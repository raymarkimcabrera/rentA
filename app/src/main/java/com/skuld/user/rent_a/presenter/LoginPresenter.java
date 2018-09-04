package com.skuld.user.rent_a.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.skuld.user.rent_a.views.LoginView;

public class LoginPresenter extends BasePresenter {

    private Context mContext;
    private LoginView mLoginView;

    public LoginPresenter(Context mContext, LoginView mLoginView) {
        this.mContext = mContext;
        this.mLoginView = mLoginView;
    }

    public void loginUser(String email, String password){
        initFirebase();

        showProgressDialog(mContext);
        Query authenticateUser = mFirebaseFirestore.collection("users")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password);

        authenticateUser.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() !=0) {
                    hideProgressDialog();
                    String userID = queryDocumentSnapshots.getDocuments().get(0).getId();
                    mLoginView.onLoginSuccess(userID);
                } else {
                    hideProgressDialog();
                    mLoginView.onLoginError();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                mLoginView.onLoginError();
            }
        });
    }

    public void loginWithFacebookOrGmail(String email){
        initFirebase();

        showProgressDialog(mContext);
        Query authenticateUser = mFirebaseFirestore.collection("users")
                .whereEqualTo("email", email);

        authenticateUser.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() !=0) {
                    hideProgressDialog();
                    String userID = queryDocumentSnapshots.getDocuments().get(0).getId();
                    mLoginView.onLoginSuccess(userID);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                mLoginView.onLoginError();
            }
        });
    }
}
