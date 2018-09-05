package com.skuld.user.rent_a.dialog;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.model.transaction.Transaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FindAVehicleDialog extends DialogFragment {

    @BindView(R.id.dateStartedTextView)
    TextView mDateStartedTextView;

    @BindView(R.id.dateEndedTextView)
    TextView mDateEndedTextView;

    @BindView(R.id.submitButton)
    Button mSubmitButton;

    private Unbinder mUnbinder;
    private static Context mContext;
    private static Transaction mTransaction;
    private int mYear, mMonth, mDay;

    public static FindAVehicleDialog build(Context context, Transaction transaction) {
        Bundle args = new Bundle();
        FindAVehicleDialog fragment = new FindAVehicleDialog();
        fragment.setArguments(args);
        mContext = context;
        mTransaction = transaction;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.Dialog_STYLE_Transaction);
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

    @OnClick({R.id.dateEndedTextView, R.id.dateStartedTextView})
    void onClick(View view){
        switch (view.getId()){
            case R.id.dateEndedTextView:
                    showDatePickerDialog(mDateEndedTextView);
                break;

            case R.id.dateStartedTextView:
                    showDatePickerDialog(mDateStartedTextView);
                break;
        }
    }
    private void showDatePickerDialog(final TextView textView) {
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
//                String dateSelected =
//                textView.setText("" + dayOfMonth + "/" + month + "/" + year);
            }

        }, mYear, mMonth, mDay);

        datePickerDialog.create();
        datePickerDialog.show();
    }
}
