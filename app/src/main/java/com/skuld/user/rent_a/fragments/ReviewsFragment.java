package com.skuld.user.rent_a.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.adapter.ReviewsRecyclerViewAdapter;
import com.skuld.user.rent_a.model.transaction.Transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReviewsFragment extends Fragment {

    @BindView(R.id.reviewsRecyclerView)
    RecyclerView mReviewsRecyclerView;

    private OnFragmentInteractionListener mListener;
    private ArrayList<? extends Transaction> mReviewsList;
    private List<Transaction> mTransactionList;
    private Context mContext;

    public ReviewsFragment() {

    }

    public static ReviewsFragment newInstance(List<Transaction> transactionList) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle args = new Bundle();
        args.putSerializable("REVIEWS", (Serializable) transactionList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        ButterKnife.bind(this, view);
        mContext = getActivity();

        getArgs();

        initUI();
        return view;
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

    private void getArgs() {
        if (getArguments() != null) {
            mTransactionList = new ArrayList<>();
            mReviewsList = (ArrayList<? extends Transaction>) getArguments().getSerializable("REVIEWS");
            mTransactionList.addAll(mReviewsList);
        }

        Log.e("getArgs", "getArgs: " + mTransactionList.size() );
    }

    private void initUI(){
        if (mTransactionList.size() > 0){
            ReviewsRecyclerViewAdapter reviewsRecyclerViewAdapter = new ReviewsRecyclerViewAdapter(mContext,mTransactionList);
            mReviewsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(mContext);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            mReviewsRecyclerView.setLayoutManager(llm);
            mReviewsRecyclerView.setAdapter(reviewsRecyclerViewAdapter);
            reviewsRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, "There are no reviews available.", Toast.LENGTH_SHORT).show();
        }

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
