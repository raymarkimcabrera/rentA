package com.skuld.user.rent_a.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.utils.PrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private final static String TAG = LoginActivity.class.getSimpleName();

    private final static int GMAIL_SIGNIN = 100;

    @BindView(R.id.loginButton)
    AppCompatButton mLoginButton;

    private String mFbToken;
    private CallbackManager callbackManager;
    private PrefUtil prefUtil;
    private Context mContext;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    // ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        mContext = this;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        prefUtil = new PrefUtil(this);
        LoginButton mLoginButton = (LoginButton) findViewById(R.id.fb_login);

        SignInButton signInButton = findViewById(R.id.gmail_signin_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);

        mLoginButton.setReadPermissions("email");
        mLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject,
                                                    GraphResponse response) {

                                // Getting FB User Data
//                                Bundle facebookData = getFacebookData(jsonObject);
                                try {
                                    Log.i(TAG, "onCompleted: " + jsonObject.getString("email"));
                                    Toast.makeText(LoginActivity.this, "" + jsonObject.getString("email"), Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(PermissionRequestActivity.newIntent(mContext));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }


            @Override
            public void onCancel() {
                Log.d(TAG, "Login attempt cancelled.");
            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
                Log.d(TAG, "Login attempt failed.");
                LoginManager.getInstance().logOut();
            }
        });
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GMAIL_SIGNIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @OnClick({R.id.loginButton, R.id.gmail_signin_button, R.id.registerButton})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                finish();
                startActivity(PermissionRequestActivity.newIntent(mContext));
                break;
            case R.id.gmail_signin_button:
                signIn();
                break;
            case R.id.registerButton:
                finish();
                startActivity(RegisterActivity.newIntent(mContext));
                break;
        }

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GMAIL_SIGNIN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            finish();
                            startActivity(PermissionRequestActivity.newIntent(mContext));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
