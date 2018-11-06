package com.skuld.user.rent_a.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.adapter.OffersFragmentAdapter;
import com.skuld.user.rent_a.fragments.ReviewsFragment;
import com.skuld.user.rent_a.fragments.SummaryFragment;
import com.skuld.user.rent_a.model.car.Car;
import com.skuld.user.rent_a.model.offer.Offer;
import com.skuld.user.rent_a.model.transaction.Transaction;
import com.skuld.user.rent_a.presenter.OffersPresenter;
import com.skuld.user.rent_a.views.OffersView;

import java.util.List;

import butterknife.BindView;

public class OfferDetailsActivity extends BaseActivity implements ReviewsFragment.OnFragmentInteractionListener, SummaryFragment.OnFragmentInteractionListener, OffersView {
    private static final String TAG = OfferDetailsActivity.class.getSimpleName();
    private static final String TRANSACTION = "TRANSACTION";

    @BindView(R.id.pager)
    ViewPager mViewPager;

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;

    private OffersFragmentAdapter mOffersFragmentAdapter;
    private Transaction mTransaction;
    private Offer mOffer;
    private OffersPresenter mOffersPresenter;

    public static Intent newIntent(Context context, Transaction transaction, Offer offer) {
        Intent intent = new Intent(context, OfferDetailsActivity.class);
        intent.putExtra(TRANSACTION, transaction);
        intent.putExtra("OFFER", offer);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOffersPresenter = new OffersPresenter(mContext, this);
        getArgs();
        mOffersPresenter.getReviews(mOffer.getCar());
    }

    private void getArgs() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mTransaction = (Transaction) extras.getSerializable(TRANSACTION);
            mOffer = (Offer) extras.getSerializable("OFFER");
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_offer_details;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onGetOffersSuccess(List<Car> carList) {

    }

    @Override
    public void onNoOffers() {

    }

    @Override
    public void onGetOffersError() {

    }

    @Override
    public void onGetReviewsSuccess(List<Transaction> transactions) {
        mOffersFragmentAdapter = new OffersFragmentAdapter(getSupportFragmentManager(), mTransaction, mOffer, transactions);
        mViewPager.setAdapter(mOffersFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onGetReviewsError() {

    }
}
