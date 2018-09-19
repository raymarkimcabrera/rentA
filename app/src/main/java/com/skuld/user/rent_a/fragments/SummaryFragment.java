package com.skuld.user.rent_a.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.model.transaction.Transaction;
import com.skuld.user.rent_a.presenter.SummaryPresenter;
import com.skuld.user.rent_a.views.SummaryView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SummaryFragment extends Fragment implements SummaryView{
    private static final String TRANSACTION = "TRANSACTION";


    @BindView(R.id.calendarView)
    CalendarView mCalendarView;

    @BindView(R.id.typeOfServiceTextView)
    TextView mTypeOfServiceTextView;

    @BindView(R.id.typeOfVehicleTextView)
    TextView mTypeOfVehicleTextView;

    @BindView(R.id.passengersTextView)
    TextView mPassengersTextView;

    @BindView(R.id.typeOfPaymentTextView)
    TextView mTypeOfPaymentTextView;

    @BindView(R.id.withDriverTextView)
    TextView mWithDriverTextView;

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private Transaction mTransaction;
    private SummaryPresenter mSummaryPresenter;

    public SummaryFragment() {

    }

    public static SummaryFragment newInstance(Transaction transaction) {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRANSACTION, transaction);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        ButterKnife.bind(this, view);
        mContext = getActivity();

        initPresenter();

        getArgs();

        initUi();

        return view;
    }

    private void getArgs() {
        mTransaction = (Transaction) getArguments().getSerializable(TRANSACTION);
    }

    private void initPresenter() {
        mSummaryPresenter = new SummaryPresenter(mContext, this);
    }

    private void initUi() {
        mTypeOfServiceTextView.setText(mTransaction.getTypeOfService());
        mTypeOfVehicleTextView.setText(mTransaction.getTypeOfVehicle());
        mPassengersTextView.setText(String.valueOf(mTransaction.getPassengers()));
        mTypeOfPaymentTextView.setText(mTransaction.getTypeOfPayment());
        mWithDriverTextView.setText(mTransaction.getDriverSpecifications());

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onBookingSuccess() {
        Toast.makeText(mContext, "Booking Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBookingError() {
        Toast.makeText(mContext, "Booking Failed", Toast.LENGTH_SHORT).show();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @OnClick(R.id.bookNowButton)
    void onClick(){
        mSummaryPresenter.bookTransaction(mTransaction);
    }
}
