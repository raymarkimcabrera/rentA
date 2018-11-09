package com.skuld.user.rent_a.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.skuld.user.rent_a.model.driver.Driver;
import com.skuld.user.rent_a.model.user.User;
import com.skuld.user.rent_a.utils.Preferences;
import com.skuld.user.rent_a.views.UsersView;

import java.io.ByteArrayOutputStream;

public class UsersPresenter extends BasePresenter {

    private Context mContext;
    private UsersView mUsersView;

    public UsersPresenter(Context context, UsersView usersView) {
        this.mContext = context;
        this.mUsersView = usersView;
    }

    public void getUserProfile(String id) {
        showProgressDialog(mContext);

        initFirebase();

        mFirebaseFirestore.collection("users").document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        hideProgressDialog();

                        User user = documentSnapshot.toObject(User.class);
                        Log.e("onGetUserSuccess", "onFailure: " + user);
                        mUsersView.onGetUserSuccess(user);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        Log.e("onGetUserError", "onFailure: ");
                        mUsersView.onGetUserError();
                    }
                });
    }

    public void getDriverProfile(String id) {
//        showProgressDialog(mContext);

        initFirebase();

        mFirebaseFirestore.collection("drivers").document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        hideProgressDialog();

                        Driver driver = documentSnapshot.toObject(Driver.class);
                        mUsersView.onGetDriverProfileSuccess(driver);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        hideProgressDialog();
                        Log.e("onGetUserError", "onFailure: ");
                        mUsersView.onGetDriverProfileError();
                    }
                });
    }

    public void updateUser(final User user, Bitmap bitmap) {

        showProgressDialog(mContext);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final byte[] data = baos.toByteArray();

        initFirebase();
        initFireStorage();

        String userImageUrl = Preferences.getString(mContext, Preferences.USER_ID) + ".jpg";
        final StorageReference userImageReference = mFirebaseStorage.getReference().child(userImageUrl);

        UploadTask uploadTask = userImageReference.putBytes(data);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }


                // Continue with the task to get the download URL
                return userImageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    user.setImageUrl(downloadUri.toString());

                    mFirebaseFirestore.collection("users").document(Preferences.getString(mContext, Preferences.USER_ID))
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    hideProgressDialog();
                                    mUsersView.onUserUpdateSuccess();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            hideProgressDialog();
                            mUsersView.onUserUpdateError();
                        }
                    });


                }
            }


        });

    }
}
