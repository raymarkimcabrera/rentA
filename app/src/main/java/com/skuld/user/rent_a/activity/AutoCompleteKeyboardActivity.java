package com.skuld.user.rent_a.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.adapter.AutoCompleteRecyclerViewAdapter;
import com.skuld.user.rent_a.model.autocomplete.SuggestionsItem;
import com.skuld.user.rent_a.model.reverse_geocoder.Locations;
import com.skuld.user.rent_a.model.reverse_geocoder.ReverseGeocoderResponse;
import com.skuld.user.rent_a.presenter.AutoCompletePresenter;
import com.skuld.user.rent_a.utils.GeneralUtils;
import com.skuld.user.rent_a.views.LocationDetailsView;
import com.skuld.user.rent_a.views.SuggestionsView;

import java.util.List;

import butterknife.BindView;

public class AutoCompleteKeyboardActivity extends BaseActivity implements LocationDetailsView, SuggestionsView, AutoCompleteRecyclerViewAdapter.OnItemClickLister {
    public static final String TAG = AutoCompleteKeyboardActivity.class.getSimpleName();

    public static final String RESULT_TEXT = "RESULT_TEXT";
    public static final String RESULT_EDIT_TEXT_ID = "RESULT_EDIT_TEXT_ID";
    public static final String RESULT_REMOVE_FOCUS_AFTER = "RESULT_REMOVE_FOCUS_AFTER";

    private static final String ARGS_EDIT_TEXT_ID = "ARGS_EDIT_TEXT_ID";
    private static final String ARGS_EDIT_TEXT_HINT = "ARGS_EDIT_TEXT_HINT";

    private static final String ARGS_REMOVE_FOCUS_AFTER = "ARGS_REMOVE_FOCUS_AFTER";


    @BindView(R.id.suggestionsRecyclerView)
    RecyclerView mSuggestionRecyclerView;

    @BindView(R.id.editText)
    EditText mEditText;

    @BindView(R.id.locationFromMapLinearLayout)
    LinearLayout mLocationFromMapLinearLayout;

    @BindView(R.id.backImageView)
    ImageView mBackImageView;

    public static Intent newIntent(Context context, int editTextId) {
        return newIntent(context, editTextId, false);
    }

    public static Intent newIntent(Context context, int editTextId, boolean removeFocusAfter) {
        Intent intent = new Intent(context, AutoCompleteKeyboardActivity.class);
        intent.putExtra(ARGS_EDIT_TEXT_ID, editTextId);
        intent.putExtra(ARGS_REMOVE_FOCUS_AFTER, removeFocusAfter);
        return intent;
    }

    private int mEditTextId;

    private Context mContext;
    private AutoCompletePresenter mAutoCompletePresenter;
    private AutoCompleteRecyclerViewAdapter mAutoCompleteRecyclerViewAdapter;
    private List<SuggestionsItem> mSuggestionsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        mEditTextId = bundle.getInt(ARGS_EDIT_TEXT_ID);

        mContext = this;

        initializeAPI();

        initUI();
    }

    private void initializeAPI() {
        mApiInterface = autoCompleteAPI();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_auto_complete_keyboard;
    }

    private void initUI() {
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                backToPreviousActivity();
            }
        });

        mLocationFromMapLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(LocationSelectorMapActivity.newIntent(mContext, mEditTextId), DashboardActivity.REQUEST_CODE_EDIT_TEXTFIELD);
            }
        });

        mEditText.setHint(mEditTextId == R.id.pickUpTextView ? "Set pick-up location" : "Set destination");

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAutoCompletePresenter = new AutoCompletePresenter(mContext, mApiInterface, (SuggestionsView) AutoCompleteKeyboardActivity.this);
                mAutoCompletePresenter.getSuggestions(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DashboardActivity.REQUEST_CODE_EDIT_TEXTFIELD) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                int editTextId = bundle.getInt(RESULT_EDIT_TEXT_ID);
                Locations locations = (Locations) bundle.getSerializable(DashboardActivity.LOCATION);

                Intent returnIntent = new Intent();
                returnIntent.putExtra(RESULT_EDIT_TEXT_ID, editTextId);
                returnIntent.putExtra(DashboardActivity.LOCATION, locations);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    private void backToPreviousActivity() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(RESULT_EDIT_TEXT_ID, mEditTextId);
        returnIntent.putExtra(RESULT_TEXT, mEditText.getText().toString());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onSuggestionSuccess(List<SuggestionsItem> suggestionsItems) {
        mSuggestionsItems = suggestionsItems;

        mAutoCompleteRecyclerViewAdapter = new AutoCompleteRecyclerViewAdapter(mContext, mSuggestionsItems, this);
        GeneralUtils.setDefaultRecyclerView(mContext,mSuggestionRecyclerView, mAutoCompleteRecyclerViewAdapter);
    }

    @Override
    public void onItemClick(String query) {
        mApiInterface = getLocationDetailsByIDAPI();

        mAutoCompletePresenter = new AutoCompletePresenter(mContext, mApiInterface, (LocationDetailsView) this);

        mAutoCompletePresenter.getLocationDetailsByID(query);
    }

    @Override
    public void onLocationDetailsSuccess(ReverseGeocoderResponse reverseGeocoderResponse) {
        Gson gson = new Gson();
        Log.e(TAG, "onLocationDetailsSuccess: " + gson.toJson(reverseGeocoderResponse) );

        Intent returnIntent = new Intent();
        returnIntent.putExtra(RESULT_EDIT_TEXT_ID, mEditTextId);
        returnIntent.putExtra(DashboardActivity.LOCATION, reverseGeocoderResponse.getLocationDetails());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
