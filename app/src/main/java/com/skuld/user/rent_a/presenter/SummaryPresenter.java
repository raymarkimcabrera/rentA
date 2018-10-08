package com.skuld.user.rent_a.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.skuld.user.rent_a.model.car.Car;
import com.skuld.user.rent_a.model.conversation.Message;
import com.skuld.user.rent_a.model.conversation.MessageList;
import com.skuld.user.rent_a.model.driver.Driver;
import com.skuld.user.rent_a.model.payment.Payment;
import com.skuld.user.rent_a.model.transaction.Transaction;
import com.skuld.user.rent_a.model.user.User;
import com.skuld.user.rent_a.utils.GeneralUtils;
import com.skuld.user.rent_a.utils.Preferences;
import com.skuld.user.rent_a.views.SummaryView;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class SummaryPresenter extends BasePresenter {

    private SummaryView mSummaryView;
    private Context mContext;

    public SummaryPresenter(Context mContext, SummaryView mSummaryView) {
        this.mSummaryView = mSummaryView;
        this.mContext = mContext;
    }

    public void bookTransaction(final Transaction transaction) {
        initFirebase();

        showProgressDialog(mContext);

        mFirebaseFirestore.collection("users").document(Preferences.getString(mContext, Preferences.USER_ID)).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        final User user = documentSnapshot.toObject(User.class);
                        String userID = documentSnapshot.getId();


                        transaction.setStatus("PENDING");
                        transaction.setUserID(userID);

                        DocumentReference newTransaction = mFirebaseFirestore.collection("transaction").document();
                        transaction.setId(newTransaction.getId());
                        mFirebaseFirestore.collection("transaction").document(transaction.getId())
                                .set(transaction)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
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


                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
