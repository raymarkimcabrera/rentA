package com.skuld.user.rent_a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

public class AutoCompleteKeyboardActivity extends AppCompatActivity {
    public static final String TAG = "CustomKeyboardActivity";

    public static final String RESULT_TEXT = "RESULT_TEXT";
    public static final String RESULT_EDIT_TEXT_ID = "RESULT_EDIT_TEXT_ID";
    public static final String RESULT_REMOVE_FOCUS_AFTER = "RESULT_REMOVE_FOCUS_AFTER";

    private static final String ARGS_EDIT_TEXT_ID = "ARGS_EDIT_TEXT_ID";
    private static final String ARGS_EDIT_TEXT_HINT = "ARGS_EDIT_TEXT_HINT";

    private static final String ARGS_REMOVE_FOCUS_AFTER = "ARGS_REMOVE_FOCUS_AFTER";

    public static Intent newIntent(Context context, int editTextId,
                                   String editTextHint) {
        return newIntent(context, editTextId,  editTextHint, false);
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
    private ImageView mBackImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_auto_complete_keyboard);

        Bundle bundle = getIntent().getExtras();
        mEditTextId = bundle.getInt(ARGS_EDIT_TEXT_ID);
        mEditTextHint = bundle.getString(ARGS_EDIT_TEXT_HINT);
        mRemoveFocusAfter = bundle.getBoolean(ARGS_REMOVE_FOCUS_AFTER);

        initUI();
    }

    private void initUI() {
        mEditText = (EditText) findViewById(R.id.editText);
        mBackImageView = (ImageView) findViewById(R.id.backImageView);

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToPreviousActivity();
            }
        });
//
        mEditText.setHint(mEditTextId == R.id.pickUpTextView ? "Set pick-up location" : "Set destination");
    }

    @Override
    public void onBackPressed() {
        backToPreviousActivity();
    }

    private void backToPreviousActivity() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(RESULT_EDIT_TEXT_ID, mEditTextId);
        returnIntent.putExtra(RESULT_TEXT, mEditText.getText().toString());
        returnIntent.putExtra(RESULT_REMOVE_FOCUS_AFTER, mRemoveFocusAfter);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }


}
