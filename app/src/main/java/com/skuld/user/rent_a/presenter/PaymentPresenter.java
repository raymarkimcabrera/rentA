package com.skuld.user.rent_a.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.skuld.user.rent_a.model.payment.Payment;
import com.skuld.user.rent_a.views.PaymentView;

public class PaymentPresenter extends BasePresenter {

    Payment mPayment;
    Context mContext;
    PaymentView mPaymentView;

    public PaymentPresenter(Context mContext, PaymentView mPaymentView) {
        this.mContext = mContext;
        this.mPaymentView = mPaymentView;
    }

    public void getPaymentByID(String paymentID) {
        showProgressDialog(mContext);

        initFirebase();

        mFirebaseFirestore.collection("payment").document(paymentID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                mPayment = documentSnapshot.toObject(Payment.class);
                hideProgressDialog();
                mPaymentView.onGetPaymentSuccess(mPayment);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                mPaymentView.onGetPaymentError();
            }
        });
    }
}
