package com.skuld.user.rent_a.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.skuld.user.rent_a.fragments.ReviewsFragment;
import com.skuld.user.rent_a.fragments.SummaryFragment;
import com.skuld.user.rent_a.model.car.Car;
import com.skuld.user.rent_a.model.transaction.Transaction;

public class OffersFragmentAdapter extends FragmentStatePagerAdapter {

    private Transaction mTransaction;
    private Car mCar;

    public OffersFragmentAdapter(FragmentManager fm, Transaction transaction, Car car) {
        super(fm);
        this.mTransaction = transaction;
        this.mCar = car;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReviewsFragment();
            case 1:
                Fragment fragment = SummaryFragment.newInstance(mTransaction, mCar);
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
