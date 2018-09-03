package com.skuld.user.rent_a.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.database.Table;
import com.skuld.user.rent_a.model.user.User;
import com.skuld.user.rent_a.utils.GeneralUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.firstNameEditText)
    EditText mFirstNameEditText;

    @BindView(R.id.middleNameEditText)
    EditText mMiddleNameEditText;

    @BindView(R.id.lastNameEditText)
    EditText mLastNameEditText;

    @BindView(R.id.contactNumberEditText)
    EditText mContactNumberEditText;

    @BindView(R.id.emailEditText)
    EditText mEmailEditText;

    @BindView(R.id.passwordEditText)
    EditText mPasswordEditText;

    @BindView(R.id.confirmPasswordEditText)
    EditText mConfirmPasswordEditText;

    private String firstName, middleName, lastName, email, password, confirmPassword, contactNumber;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_register;
    }


    @OnClick({R.id.signUpButton, R.id.loginTextView})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUpButton:
                signUpUser();
                break;

            case R.id.loginButton:
                break;
        }
    }

    private void signUpUser() {
        prepareData();
        if (isValidInputs()) {

            mDatabase = getFirebaseDatabase();

            User user = new User();
            user.setFirstName(firstName);
            if (!middleName.isEmpty())
                user.setMiddleName(middleName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setContactNumber(contactNumber);
            user.setPassword(password);

            mDatabase.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(mContext, "Successfully added document ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext, "Didn't save to the database ", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(mContext, "Please input all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidInputs() {
        if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && GeneralUtils.isValidEmail(email)) {
            if (String.valueOf(contactNumber).length() <= 11) {
                if (password.equals(confirmPassword)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private void prepareData() {
        firstName = mFirstNameEditText.getText().toString().trim();
        middleName = mMiddleNameEditText.getText().toString().trim();
        lastName = mLastNameEditText.getText().toString().trim();
        email = mEmailEditText.getText().toString().trim();
        password = mPasswordEditText.getText().toString().trim();
        confirmPassword = mConfirmPasswordEditText.getText().toString().trim();
        contactNumber = mContactNumberEditText.getText().toString().trim();
    }
}
