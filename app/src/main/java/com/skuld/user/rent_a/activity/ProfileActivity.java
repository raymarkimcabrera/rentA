package com.skuld.user.rent_a.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.model.driver.Driver;
import com.skuld.user.rent_a.model.user.User;
import com.skuld.user.rent_a.presenter.UsersPresenter;
import com.skuld.user.rent_a.utils.FileUtil;
import com.skuld.user.rent_a.utils.ImageUtil;
import com.skuld.user.rent_a.utils.NavigationUtils;
import com.skuld.user.rent_a.utils.Preferences;
import com.skuld.user.rent_a.views.UsersView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity implements UsersView {

    public static final int REQUEST_CODE_PERMISSION_CAMERA_AND_STORAGE = 103;
    public static final int REQUEST_IMAGE_CAPTURE = 100;

    @BindView(R.id.userProfileImageView)
    CircleImageView mUserProfileImageView;

    @BindView(R.id.firstNameEditText)
    EditText mFirstNameEditText;

    @BindView(R.id.middleNameEditText)
    EditText mMiddleNameEditText;

    @BindView(R.id.lastNameEditText)
    EditText mLastNameEditText;

    @BindView(R.id.emailAddressEditText)
    EditText mEmailEditText;

    @BindView(R.id.contactNumberEditText)
    EditText mContactNumberEditText;


    private UsersPresenter mUsersPresenter;
    private User mUser;
    private Uri mFileUri;

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, ProfileActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUsersPresenter.getUserProfile(Preferences.getString(mContext, Preferences.USER_ID));
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_profile;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case REQUEST_IMAGE_CAPTURE:
                        String imagePath = mFileUri.toString();
                        Log.i("loadImage", "loadImage Not Cropped: String: " + imagePath);
                        ImageUtil.loadImageFromUrl(mContext, mUserProfileImageView, imagePath);
                        break;
                    default:
                        Toast.makeText(mContext, "There was a problem with your request", Toast.LENGTH_SHORT).show();
                        break;
                }
            } else {
                // User Cancelled
            }
        }
    }

    @Override
    protected void permissionGranted(int requestCode) {
        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA_AND_STORAGE) {
            FileUtil mFileUtil = new FileUtil(mContext);
//            Uri fileUri = mFileUtil.generateImageFileUri();
            Uri fileUri = FileProvider.getUriForFile(mContext,
                    mContext.getApplicationContext().getPackageName()
                            + ".my.package.name.provider", mFileUtil.generateImageFile());
            if (fileUri != null) {
                mFileUri = fileUri;
                NavigationUtils.startCameraActivity(this, mFileUri, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void permissionDenied(String message) {
        if (message.equals("NEVERASKAGAIN")) {
            Toast.makeText(mContext, "Cannot open camera", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetUserSuccess(User user) {
        mUser = user;

        initUi();
    }

    @Override
    public void onGetUserError() {

    }

    @Override
    public void onUserUpdateSuccess() {
        Toast.makeText(mContext, "Successfully update user profile.", Toast.LENGTH_SHORT).show();
        mUsersPresenter.getUserProfile(Preferences.getString(mContext, Preferences.USER_ID));
    }

    @Override
    public void onUserUpdateError() {

    }

    @Override
    public void onGetDriverProfileSuccess(Driver driver) {

    }

    @Override
    public void onGetDriverProfileError() {

    }

    @OnClick({R.id.saveUserButton, R.id.userProfileImageView})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveUserButton:

                User user = mUser;
                user.setFirstName(mFirstNameEditText.getText().toString());
                user.setMiddleName(mMiddleNameEditText.getText().toString());
                user.setLastName(mLastNameEditText.getText().toString());
                user.setEmail(mEmailEditText.getText().toString());
                user.setContactNumber(mContactNumberEditText.getText().toString());

                mUsersPresenter.updateUser(user, ((BitmapDrawable) mUserProfileImageView.getDrawable()).getBitmap());
                break;
            case R.id.userProfileImageView:
                checkForPermissions(REQUEST_CODE_PERMISSION_CAMERA_AND_STORAGE, android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE);
                break;
        }
    }

    private void initPresenter() {
        mUsersPresenter = new UsersPresenter(mContext, this);
    }

    private void initUi() {
        mFirstNameEditText.setText(mUser.getFirstName());
        mMiddleNameEditText.setText(mUser.getMiddleName());
        mLastNameEditText.setText(mUser.getLastName());
        mEmailEditText.setText(mUser.getEmail());
        mContactNumberEditText.setText(mUser.getContactNumber());
        if (!mUser.getImageUrl().isEmpty())
            ImageUtil.loadImageFromUrl(mContext, mUserProfileImageView, mUser.getImageUrl());
    }


}
