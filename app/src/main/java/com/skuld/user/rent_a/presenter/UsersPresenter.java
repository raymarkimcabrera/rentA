package com.skuld.user.rent_a.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.skuld.user.rent_a.model.user.User;
import com.skuld.user.rent_a.views.UsersView;

public class UsersPresenter extends BasePresenter {

    private Context mContext;
    private UsersView mUsersView;

    public UsersPresenter(Context context, UsersView usersView) {
        this.mContext = context;
        this.mUsersView = usersView;
    }

    public void getUserProfile(String id) {
        showProgressDialog(mContext);

        initFirebase();

        mFirebaseFirestore.collection("users").document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        hideProgressDialog();

                        User user = documentSnapshot.toObject(User.class);
                        Log.e("onGetUserSuccess", "onFailure: " + user);
                        mUsersView.onGetUserSuccess(user);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        Log.e("onGetUserError", "onFailure: ");
                        mUsersView.onGetUserError();
                    }
                });
    }
}
