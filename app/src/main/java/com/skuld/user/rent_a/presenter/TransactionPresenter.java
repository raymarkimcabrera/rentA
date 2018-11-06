package com.skuld.user.rent_a.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.skuld.user.rent_a.model.car.Car;
import com.skuld.user.rent_a.model.conversation.Message;
import com.skuld.user.rent_a.model.conversation.MessageList;
import com.skuld.user.rent_a.model.offer.Offer;
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
                                        mTransactionView.onTransactionStatusUpdateSuccess(new Transaction());
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
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        mTransactionView.onTransactionStatusUpdateError();
                    }
                });

    }

    public void acceptOffer(final Transaction transaction, Offer offer) {

        initFirebase();
        showProgressDialog(mContext);
        // Remove offers that is not accepted
        List<Offer> emptyOffers = new ArrayList<>();
        final Car car = offer.getCar();

        car.setTransactionID(transaction.getId());

        offer.setCar(car);

        transaction.setOfferAccepted(offer);
        transaction.setOfferList(emptyOffers);
        mFirebaseFirestore.collection("messages").document()
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        MessageList messageList = new MessageList();
                        List<Message> messages = new ArrayList<>();

                        messageList.setId(documentSnapshot.getId());
                        messageList.setThread(messages);
                        transaction.setConversationID(documentSnapshot.getId());

                        mFirebaseFirestore.collection("messages")
                                .document(messageList.getId())
                                .set(messageList)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        mFirebaseFirestore.collection("transaction").document(transaction.getId())
                                                .set(transaction)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        mFirebaseFirestore.collection("payment").document(transaction.getPaymentID())
                                                                .update("status", "PENDING")
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {

                                                                        mFirebaseFirestore.collection("car").document(car.getId())
                                                                                .set(car)
                                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                    @Override
                                                                                    public void onSuccess(Void aVoid) {
                                                                                        hideProgressDialog();
                                                                                        mTransactionView.onTransactionStatusUpdateSuccess(transaction);
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
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        hideProgressDialog();
                                                                        mTransactionView.onTransactionStatusUpdateError();
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
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        hideProgressDialog();
                                        mTransactionView.onTransactionStatusUpdateError();
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
