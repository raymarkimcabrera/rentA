package com.skuld.user.rent_a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.skuld.user.rent_a.activity.DashboardActivity;
import com.skuld.user.rent_a.activity.LocationSelectorMapActivity;
import com.skuld.user.rent_a.model.autocomplete.SuggestionList;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutoCompleteKeyboardActivity extends BaseActivity implements Callback<SuggestionList>{
    public static final String TAG = "AutoCompleteKeyboardActivity";

    public static final String RESULT_TEXT = "RESULT_TEXT";
    public static final String RESULT_EDIT_TEXT_ID = "RESULT_EDIT_TEXT_ID";
    public static final String RESULT_REMOVE_FOCUS_AFTER = "RESULT_REMOVE_FOCUS_AFTER";

    private static final String ARGS_EDIT_TEXT_ID = "ARGS_EDIT_TEXT_ID";
    private static final String ARGS_EDIT_TEXT_HINT = "ARGS_EDIT_TEXT_HINT";

    private static final String ARGS_REMOVE_FOCUS_AFTER = "ARGS_REMOVE_FOCUS_AFTER";

    public static Intent newIntent(Context context, int editTextId,
                                   String editTextHint) {
        return newIntent(context, editTextId, editTextHint, false);
    }

    public static Intent newIntent(Context context, int editTextId,
                                   String editTextHint, boolean removeFocusAfter) {
        Intent intent = new Intent(context, AutoCompleteKeyboardActivity.class);
        intent.putExtra(ARGS_EDIT_TEXT_ID, editTextId);
        intent.putExtra(ARGS_EDIT_TEXT_HINT, editTextHint);
        intent.putExtra(ARGS_REMOVE_FOCUS_AFTER, removeFocusAfter);
        return intent;
    }

    private int mEditTextId;
    private String mEditTextText;
    private String mEditTextHint;
    private boolean mRemoveFocusAfter;

    private EditText mEditText;
    private LinearLayout mLocationFromMapLinearLayout;
    private ImageView mBackImageView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        mEditTextId = bundle.getInt(ARGS_EDIT_TEXT_ID);
        mEditTextHint = bundle.getString(ARGS_EDIT_TEXT_HINT);
        mRemoveFocusAfter = bundle.getBoolean(ARGS_REMOVE_FOCUS_AFTER);

        mContext = this;

        initializeAPI();
        initUI();
//
    }

    private void initializeAPI() {

        mApiInterface = autoCompleteAPI();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_auto_complete_keyboard;
    }

    private void initUI() {
        mEditText = (EditText) findViewById(R.id.editText);
        mBackImageView = (ImageView) findViewById(R.id.backImageView);
        mLocationFromMapLinearLayout = (LinearLayout) findViewById(R.id.locationFromMapLinearLayout);

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToPreviousActivity();
            }
        });

        mLocationFromMapLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivityForResult(LocationSelectorMapActivity.newIntent(mContext), DashboardActivity.REQUEST_CODE_MAP_LOCATION_SELECT);
            }
        });

        mEditText.setHint(mEditTextId == R.id.pickUpTextView ? "Set pick-up location" : "Set destination");

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Call<SuggestionList> suggestionsItemCall = mApiInterface.getSuggestions(s.toString());

                suggestionsItemCall.enqueue(AutoCompleteKeyboardActivity.this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        backToPreviousActivity();
    }

    @Override
    protected void onStop() {
//        compositeDisposable.clear();
        super.onStop();
    }

    private void backToPreviousActivity() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(RESULT_EDIT_TEXT_ID, mEditTextId);
        returnIntent.putExtra(RESULT_TEXT, mEditText.getText().toString());
        returnIntent.putExtra(RESULT_REMOVE_FOCUS_AFTER, mRemoveFocusAfter);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


    @Override
    public void onResponse(Call<SuggestionList> call, Response<SuggestionList> response) {
        Gson gson = new Gson();
        if(response.isSuccessful()) {
            SuggestionList changesList =  (SuggestionList) response.body();
            System.out.print("onResponse" + gson.toJson(changesList));
            Log.e("GET", "onSuccess: " + changesList );
        } else {
            try {
                Log.e("ERROR", "onResponse: " + response.errorBody().string() );
                JSONObject jObjError = new JSONObject(response.errorBody().string());

                Log.e("GET", "onErrorBody: " + jObjError.get("message") );
                Log.e("GET", "onErrorBody: " + jObjError.get("code") );
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<SuggestionList> call, Throwable t) {
        t.printStackTrace();
        Log.e("GET", "onFailure: " + t.getMessage() );
    }
}
