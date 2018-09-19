package com.skuld.user.rent_a.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.skuld.user.rent_a.model.transaction.Transaction;
import com.skuld.user.rent_a.views.SummaryView;

public class SummaryPresenter extends BasePresenter {

    private SummaryView mSummaryView;
    private Context mContext;

    public SummaryPresenter(Context mContext, SummaryView mSummaryView) {
        this.mSummaryView = mSummaryView;
        this.mContext = mContext;
    }

    public void bookTransaction(Transaction transaction){

        initFirebase();

        showProgressDialog(mContext);

        mFirebaseFirestore.collection("transactions").add(transaction).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                hideProgressDialog();
                mSummaryView.onBookingSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                mSummaryView.onBookingError();
            }
        });
    }
}
