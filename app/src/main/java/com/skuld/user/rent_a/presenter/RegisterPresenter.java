package com.skuld.user.rent_a.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skuld.user.rent_a.model.user.User;
import com.skuld.user.rent_a.views.RegisterView;

public class RegisterPresenter extends BasePresenter {

    private FirebaseFirestore mFirebaseFirestore;
    private RegisterView mRegisterView;
    private Context mContext;

    public RegisterPresenter(Context context, RegisterView registerView) {
        this.mContext = context;
        this.mRegisterView = registerView;
    }

    public void registerUser(String firstName, String middleName, String lastName, String email, String contactNumber, String password) {

        mFirebaseFirestore = FirebaseFirestore.getInstance();

        User user = new User();
        user.setFirstName(firstName);
        if (!middleName.isEmpty())
            user.setMiddleName(middleName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setContactNumber(contactNumber);
        user.setPassword(password);

        showProgressDialog(mContext);
        mFirebaseFirestore.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        hideProgressDialog();
                        mRegisterView.onRegisterSuccess(documentReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        mRegisterView.onRegisterFailed(e.getMessage());
                    }
                });
    }
}
