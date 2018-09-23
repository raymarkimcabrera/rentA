package com.skuld.user.rent_a.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.skuld.user.rent_a.model.car.Car;
import com.skuld.user.rent_a.model.conversation.MessageList;
import com.skuld.user.rent_a.model.payment.Payment;
import com.skuld.user.rent_a.model.transaction.Transaction;
import com.skuld.user.rent_a.views.SummaryView;

import org.w3c.dom.Document;

public class SummaryPresenter extends BasePresenter {

    private SummaryView mSummaryView;
    private Context mContext;

    public SummaryPresenter(Context mContext, SummaryView mSummaryView) {
        this.mSummaryView = mSummaryView;
        this.mContext = mContext;
    }

    public void bookTransaction(final Transaction transaction, Car car) {

        initFirebase();

        showProgressDialog(mContext);

        final MessageList messageList = new MessageList();
        Payment payment = new Payment();

        payment.setStatus("UNPAID");
        payment.setTotalAmount(car.getPrice());

        DocumentReference paymentDocumentReference = mFirebaseFirestore.collection("payment").document();
        String paymentID = paymentDocumentReference.getId();

        payment.setId(paymentID);
        transaction.setPaymentID(paymentID);
        transaction.setStatus("PENDING");

        mFirebaseFirestore.collection("payment").document(paymentID).set(payment).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mFirebaseFirestore.collection("messages").add(messageList).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        transaction.setConversationID(documentReference.getId());
                        DocumentReference newTransaction = mFirebaseFirestore.collection("transactions").document();

                        transaction.setId(newTransaction.getId());
                        mFirebaseFirestore.collection("transactions").document(transaction.getId())
                                .set(transaction)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        hideProgressDialog();
                                        mSummaryView.onBookingSuccess();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
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
                        hideProgressDialog();
                        mSummaryView.onBookingError();
                    }
                });
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
