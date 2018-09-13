package com.skuld.user.rent_a.dialog;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.model.transaction.Transaction;
import com.skuld.user.rent_a.utils.ModelUtil;
import com.skuld.user.rent_a.utils.Preferences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.facebook.internal.FacebookDialogFragment.TAG;

public class FindAVehicleDialog extends DialogFragment {

    private final static String EMPTY_DATE = "Click to select Date";
    private final static String EMPTY_TIME = "Click to select Time";
    private final static String ALL_OUT = "ALL_OUT";
    private final static String SEPERATE_PAYMENTS = "SEPERATE_PAYMENTS";
    private final static String FULLY_PAID = "FULLY_PAID";
    private final static String PARTIALLY_PAID = "PARTIALLY_PAID";
    private final static String PENDING = "PENDING";

    @BindView(R.id.dateStartedTextView)
    TextView mDateStartedTextView;

    @BindView(R.id.dateEndedTextView)
    TextView mDateEndedTextView;

    @BindView(R.id.timeStartedTextView)
    TextView mTimeStartedTextView;

    @BindView(R.id.timeEndedTextView)
    TextView mTimeEndedTextView;


    @BindView(R.id.submitButton)
    Button mSubmitButton;

    @BindView(R.id.typeOfVehicleSpinner)
    Spinner mTypeOfVehicleSpinner;

    @BindView(R.id.typeOfPaymentRadioGroup)
    RadioGroup mTypeOfPaymentRadioGroup;

    @BindView(R.id.allOutRadioButton)
    RadioButton mAllOutRadioButton;

    @BindView(R.id.separatePaymentRadioButton)
    RadioButton mSeparateRadioButton;

    @BindView(R.id.passengerNumberEditText)
    EditText mPassengerNumberEditText;

    @BindView(R.id.driverRadioGroup)
    RadioGroup mDriverRadioGroup;

    @BindView(R.id.withDriverRadioButton)
    RadioButton mWithDriverRadioButton;

    @BindView(R.id.withoutDriverRadioButton)
    RadioButton mWithOutDriverRadioButton;


    private Unbinder mUnbinder;
    private static Context mContext;
    private static Transaction mTransaction;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static OnClickListener mOnClickListener;

    public static FindAVehicleDialog build(Context context, Transaction transaction, OnClickListener onClickListener) {
        Bundle args = new Bundle();
        FindAVehicleDialog fragment = new FindAVehicleDialog();
        fragment.setArguments(args);
        mContext = context;
        mTransaction = transaction;
        mOnClickListener = onClickListener;
        return fragment;
    }

    public interface OnClickListener{
        void onSubmit(Transaction transaction);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_STYLE_Transaction);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_find_a_vehicle_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick({R.id.dateEndedTextView, R.id.dateStartedTextView, R.id.timeEndedTextView, R.id.timeStartedTextView, R.id.submitButton})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.dateEndedTextView:
                showDatePickerDialog(mDateEndedTextView);
                break;

            case R.id.dateStartedTextView:
                showDatePickerDialog(mDateStartedTextView);
                break;

            case R.id.timeEndedTextView:
                showTimePickerDialog(mTimeEndedTextView);
                break;

            case R.id.timeStartedTextView:
                showTimePickerDialog(mTimeStartedTextView);
                break;

            case R.id.submitButton:
                if (validateInputs()) {
                    prepareData();
                } else {
                    Toast.makeText(mContext, "Please check all the fields", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean validateInputs() {
        String dateStarted = mDateStartedTextView.getText().toString();
        String dateEnded = mDateEndedTextView.getText().toString();
        String timeStarted = mTimeStartedTextView.getText().toString();
        String timeEnded = mTimeEndedTextView.getText().toString();
        if (mPassengerNumberEditText.getText().toString().trim().isEmpty()){
            return false;
        }
        int passengers = Integer.parseInt(mPassengerNumberEditText.getText().toString());

        if (!dateStarted.equals(EMPTY_DATE) && !dateEnded.equals(EMPTY_DATE) &&
                !timeStarted.equals(EMPTY_TIME) && !timeEnded.equals(EMPTY_TIME) &&
                passengers > 0 && mTypeOfPaymentRadioGroup.getCheckedRadioButtonId() != -1 &&
                mDriverRadioGroup.getCheckedRadioButtonId() != -1) {
            return true;
        }
        return false;
    }

    private void prepareData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa");
        Date dateStarted = new Date(), dateEnded = new Date();

        try {
            dateStarted = simpleDateFormat.parse(mDateStartedTextView.getText().toString() + " " + mTimeStartedTextView.getText().toString());
            dateEnded = simpleDateFormat.parse(mDateEndedTextView.getText().toString() + " " + mTimeEndedTextView.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        mTransaction = new Transaction();
        Log.i(TAG, "prepareData: " + dateStarted);
        Log.i(TAG, "prepareData: " + dateEnded);
        Timestamp startTimeStamp = new Timestamp(dateStarted);
        Timestamp endTimeStamp = new Timestamp(dateEnded);

        Log.i(TAG, "prepareData: " + startTimeStamp);
        Log.i(TAG, "prepareData: " + endTimeStamp);
        mTransaction.setUserID(Preferences.getString(mContext, Preferences.USER_ID));
        mTransaction.setStartDate(startTimeStamp);
        mTransaction.setEndDate(endTimeStamp);
        mTransaction.setTypeOfVehicle(mTypeOfVehicleSpinner.getSelectedItem().toString());
        mTransaction.setPassengers(Integer.parseInt(mPassengerNumberEditText.getText().toString()));
        mTransaction.setTypeOfPayment(mTypeOfPaymentRadioGroup.getCheckedRadioButtonId() == R.id.allOutRadioButton ? ALL_OUT : SEPERATE_PAYMENTS);
        mTransaction.setWithDriver(mDriverRadioGroup.getCheckedRadioButtonId() == R.id.withDriverRadioButton);
        mTransaction.setPaymentStatus(PENDING);

        mOnClickListener.onSubmit(mTransaction);
    }

    private void showDatePickerDialog(final TextView textView) {
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                String dateFormatted = simpleDateFormat.format(calendar.getTime());
                textView.setText(dateFormatted);

            }

        }, mYear, mMonth, mDay);

        datePickerDialog.create();
        datePickerDialog.show();
    }

    private void showTimePickerDialog(final TextView textView) {
        final Calendar calendar = Calendar.getInstance();

        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(mContext);
                textView.setText(dateFormat.format(calendar.getTime()));

            }
        }, mHour, mMinute, false);

        timePickerDialog.create();
        timePickerDialog.show();
    }
}
