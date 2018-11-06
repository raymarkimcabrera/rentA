package com.skuld.user.rent_a.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.skuld.user.rent_a.fragments.ReviewsFragment;
import com.skuld.user.rent_a.fragments.SummaryFragment;
import com.skuld.user.rent_a.model.car.Car;
import com.skuld.user.rent_a.model.offer.Offer;
import com.skuld.user.rent_a.model.transaction.Transaction;

import java.util.List;

public class OffersFragmentAdapter extends FragmentStatePagerAdapter {

    private Transaction mTransaction;
    private Offer mOffer;
    private List<Transaction> mReviewsTransactionList;

    public OffersFragmentAdapter(FragmentManager fm, Transaction transaction, Offer offer, List<Transaction> reviewsList) {
        super(fm);
        this.mTransaction = transaction;
        this.mOffer = offer;
        this.mReviewsTransactionList = reviewsList;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment reviewsFragment = ReviewsFragment.newInstance(mReviewsTransactionList);
                return reviewsFragment;
            case 1:
                Fragment fragment = SummaryFragment.newInstance(mTransaction, mOffer);
                return fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Reviews";
            case 1:
                return "Summary";
            default:
                return null;
        }
    }
}
