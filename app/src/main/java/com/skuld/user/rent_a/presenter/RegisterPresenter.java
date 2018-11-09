package com.skuld.user.rent_a.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.skuld.user.rent_a.activity.PermissionRequestActivity;
import com.skuld.user.rent_a.model.user.User;
import com.skuld.user.rent_a.views.GoogleLoginView;
import com.skuld.user.rent_a.views.RegisterView;

public class RegisterPresenter extends BasePresenter {

    private RegisterView mRegisterView;
    private GoogleLoginView mGoogleLoginView;
    private Context mContext;

    public RegisterPresenter(Context context, RegisterView registerView) {
        this.mContext = context;
        this.mRegisterView = registerView;
    }

    public RegisterPresenter(Context context, GoogleLoginView googleLoginView) {
        this.mContext = context;
        this.mGoogleLoginView = googleLoginView;
    }

    public void registerUser(String firstName, String middleName, String lastName, String email, String contactNumber, String password) {

        initFirebase();

        final User user = new User();
        user.setFirstName(firstName);
        if (!middleName.isEmpty())
            user.setMiddleName(middleName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setContactNumber(contactNumber);
        user.setPassword(password);

        user.setImageUrl("");

        showProgressDialog(mContext);

        Query checkEmailIfUsed = mFirebaseFirestore.collection("users")
                .whereEqualTo("email", user.getEmail());

        checkEmailIfUsed.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() != 0) {
                    hideProgressDialog();
                    mRegisterView.onRegisterFailed("The email you registered is already used");
                } else {
                    mFirebaseFirestore.collection("users")
                            .document()
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    user.setId(documentSnapshot.getId());
                                    mFirebaseFirestore.collection("users")
                                            .document(documentSnapshot.getId())
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    hideProgressDialog();
                                                    mRegisterView.onRegisterSuccess();
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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public void firebaseAuthWithGoogle(Activity activity, final FirebaseAuth firebaseAuth, GoogleSignInAccount googleSignInAccount) {
        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        showProgressDialog(mContext);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            hideProgressDialog();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            mGoogleLoginView.onGmailLoginSuccess(user);
                        } else {
                            hideProgressDialog();
                            mGoogleLoginView.onGmailLoginError(task.getException().getMessage());
                        }
                    }
                });
    }
}
