package com.skuld.user.rent_a.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.skuld.user.rent_a.model.transaction.Transaction;
import com.skuld.user.rent_a.views.TransactionView;

import java.util.ArrayList;
import java.util.List;

public class TransactionPresenter extends BasePresenter {

    private Context mContext;
    private TransactionView mTransactionView;
    private List<Transaction> mTransactionList;

    public TransactionPresenter(Context mContext, TransactionView mTransactionView) {
        this.mContext = mContext;
        this.mTransactionView = mTransactionView;
    }

    public void getTransactions() {
        mTransactionList = new ArrayList<>();

        initFirebase();

        showProgressDialog(mContext);

        Query getTransactionQuery = mFirebaseFirestore.collection("transaction");

        getTransactionQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                hideProgressDialog();
                if (queryDocumentSnapshots.getDocuments().size() != 0) {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        mTransactionList.add(documentSnapshot.toObject(Transaction.class));
                    }

                    mTransactionView.onGetTransactionViewSuccess(mTransactionList);
                } else {
                    mTransactionView.onNoTransaction();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();

                mTransactionView.onGetTransactionViewError();
            }
        });
    }

    public void getUserTransactions(String userID) {
        mTransactionList = new ArrayList<>();

        initFirebase();

        showProgressDialog(mContext);

        Query getTransactionQuery = mFirebaseFirestore.collection("transaction").whereEqualTo("userID", userID);

        getTransactionQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                hideProgressDialog();
                if (queryDocumentSnapshots.getDocuments().size() != 0) {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        mTransactionList.add(documentSnapshot.toObject(Transaction.class));
                    }

                    mTransactionView.onGetTransactionViewSuccess(mTransactionList);
                } else {
                    mTransactionView.onNoTransaction();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();

                mTransactionView.onGetTransactionViewError();
            }
        });
    }

    public void updateTransactionStatusById(final Transaction transaction, final String status) {
        initFirebase();

        showProgressDialog(mContext);

        mFirebaseFirestore.collection("transaction").document(transaction.getId())
                .update("status", status)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        mFirebaseFirestore.collection("payment").document(transaction.getPaymentID())
                                .update("status", status)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        hideProgressDialog();
                                        mTransactionView.onTransactionStatusUpdateSuccess();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        hideProgressDialog();
                                        mTransactionView.onTransactionStatusUpdateSuccess();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        mTransactionView.onTransactionStatusUpdateError();
                    }
                });

    }
}
