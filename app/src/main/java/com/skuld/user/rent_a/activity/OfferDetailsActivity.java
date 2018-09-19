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
import com.skuld.user.rent_a.model.transaction.Transaction;

import butterknife.BindView;

public class OfferDetailsActivity extends BaseActivity implements ReviewsFragment.OnFragmentInteractionListener, SummaryFragment.OnFragmentInteractionListener{
    private static final String TAG = OfferDetailsActivity.class.getSimpleName();
    private static final String TRANSACTION = "TRANSACTION";

    @BindView(R.id.pager)
    ViewPager mViewPager;

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;

    private OffersFragmentAdapter mOffersFragmentAdapter;
    private Transaction mTransaction;

    public static Intent newIntent(Context context, Transaction transaction) {
        Intent intent = new Intent(context, OfferDetailsActivity.class);
        intent.putExtra(TRANSACTION, transaction);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getArgs();
        initializeViews();
    }

    private void getArgs() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mTransaction = (Transaction) extras.getSerializable(TRANSACTION);
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_offer_details;
    }

    private void initializeViews() {
        mOffersFragmentAdapter = new OffersFragmentAdapter(getSupportFragmentManager(), mTransaction);
        mViewPager.setAdapter(mOffersFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
