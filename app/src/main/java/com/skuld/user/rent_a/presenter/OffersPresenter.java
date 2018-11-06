package com.skuld.user.rent_a.presenter;

import android.app.DownloadManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.skuld.user.rent_a.activity.OffersActivity;
import com.skuld.user.rent_a.model.car.Car;
import com.skuld.user.rent_a.model.offer.Offer;
import com.skuld.user.rent_a.model.transaction.Transaction;
import com.skuld.user.rent_a.views.OffersView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OffersPresenter extends BasePresenter {
    private static final String TAG = OffersPresenter.class.getSimpleName();

    private Context mContext;
    private OffersView mOffersView;
    private List<Car> mCarList;

    public OffersPresenter(Context mContext, OffersView mOffersView) {
        this.mContext = mContext;
        this.mOffersView = mOffersView;
    }

    public void getOffers() {
        mCarList = new ArrayList<>();

        initFirebase();

        showProgressDialog(mContext);

        Query getCarListQuery = mFirebaseFirestore.collection("car");

        getCarListQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                hideProgressDialog();
                if (queryDocumentSnapshots.getDocuments().size() != 0) {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        mCarList.add(documentSnapshot.toObject(Car.class));
                    }
                    mOffersView.onGetOffersSuccess(mCarList);
                } else {
                    mOffersView.onNoOffers();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                mOffersView.onGetOffersError();

            }
        });
    }

    public void getReviews(Car car) {
        showProgressDialog(mContext);

        initFirebase();
        Query getReviews = mFirebaseFirestore.collection("transaction")
                .whereEqualTo("offerAccepted.car.id", car.getId());

        getReviews.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Transaction> transactionList = new ArrayList<>();
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    transactionList.add(documentSnapshot.toObject(Transaction.class));
                }
                hideProgressDialog();
                Log.e("getReviews", "onSuccess: " + transactionList.size());
                mOffersView.onGetReviewsSuccess(transactionList);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                mOffersView.onGetReviewsError();
            }
        });
    }
}
