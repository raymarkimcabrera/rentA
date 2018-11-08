package com.skuld.user.rent_a.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

class BasePresenter {

    ProgressDialog progressDialog;
    protected FirebaseFirestore mFirebaseFirestore;
    protected FirebaseStorage mFirebaseStorage;

    void showProgressDialog(Context context) {
        if (progressDialog == null){
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    void hideProgressDialog(){
        if (progressDialog!= null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    void initFirebase(){
        mFirebaseFirestore = FirebaseFirestore.getInstance();
    }

    void initFireStorage(){
        mFirebaseStorage = FirebaseStorage.getInstance();
    }
}
