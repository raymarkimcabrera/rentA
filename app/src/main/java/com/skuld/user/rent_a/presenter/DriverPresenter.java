package com.skuld.user.rent_a.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.skuld.user.rent_a.model.driver.Driver;
import com.skuld.user.rent_a.views.DriverView;

public class DriverPresenter extends BasePresenter {

    private Context mContext;
    private DriverView mDriverView;

    public DriverPresenter(Context context, DriverView driverView) {
        this.mContext = context;
        this.mDriverView = driverView;
    }

    public void getDriverProfile(String id){

        showProgressDialog(mContext);

        initFirebase();

        mFirebaseFirestore.collection("drivers").document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        hideProgressDialog();
                        mDriverView.onGetDriverProfileSuccess(documentSnapshot.toObject(Driver.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        mDriverView.onGetDriverProfileError();
                    }
                });
    }
}
