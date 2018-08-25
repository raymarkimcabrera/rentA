package com.skuld.user.rent_a.presenter;

import android.app.ProgressDialog;
import android.content.Context;

class BasePresenter {
    ProgressDialog progressDialog;

    void showProgressDialog(Context context) {
        if (progressDialog == null){
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
    }

    void hideProgressDialog(){
        if (progressDialog!= null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
