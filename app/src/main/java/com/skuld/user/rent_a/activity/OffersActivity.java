package com.skuld.user.rent_a.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.adapter.OffersRecyclerViewAdapter;
import com.skuld.user.rent_a.model.car.Car;
import com.skuld.user.rent_a.model.transaction.Transaction;
import com.skuld.user.rent_a.presenter.OffersPresenter;
import com.skuld.user.rent_a.presenter.TransactionPresenter;
import com.skuld.user.rent_a.utils.ModelUtil;
import com.skuld.user.rent_a.utils.Preferences;
import com.skuld.user.rent_a.views.OffersView;
import com.skuld.user.rent_a.views.TransactionView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OffersActivity extends BaseActivity implements OffersView, TransactionView {

    private static final String TAG = OffersActivity.class.getSimpleName();
    private static final String TRANSACTION = "TRANSACTION";

    @BindView(R.id.offersRecyclerView)
    RecyclerView mOfferRecyclerView;

    private List<Car> mCarList;
    private List<Transaction> mTransactionList;
    private OffersRecyclerViewAdapter mOffersRecyclerViewAdapter;
    private Car mCar;

    private OffersPresenter mOffersPresenter;
    private TransactionPresenter mTransactionPresenter;
    private Transaction mTransaction;

    public static Intent newIntent(Context context, Transaction transaction) {
        Intent intent = new Intent(context, OffersActivity.class);
        intent.putExtra(TRANSACTION, transaction);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        initPresenter();

        getArgs();
//        mCarList = new ArrayList<>();
        mTransactionList = new ArrayList<>();

        mTransactionPresenter.getTransactions();
    }

    private void getArgs() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mTransaction = (Transaction) extras.getSerializable(TRANSACTION);
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_offers;
    }

    private void initPresenter() {
        mOffersPresenter = new OffersPresenter(mContext, this);
        mTransactionPresenter = new TransactionPresenter(mContext, this);
    }

    @Override
    public void onGetOffersSuccess(List<Car> carList) {
        mOffersRecyclerViewAdapter = new OffersRecyclerViewAdapter(mContext, carList, new OffersRecyclerViewAdapter.OnClickListener() {
            @Override
            public void onCarSelected(Car car) {
                mTransaction.setCarID(car.getId());
                startActivity(OfferDetailsActivity.newIntent(mContext,mTransaction));
            }
        }, mTransactionList);

        mOfferRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mOfferRecyclerView.setLayoutManager(llm);
        mOfferRecyclerView.setAdapter(mOffersRecyclerViewAdapter);
        mOffersRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoOffers() {
        Toast.makeText(mContext, "No offers available", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetOffersError() {

    }

    @Override
    public void onGetTransactionViewSuccess(List<Transaction> transactionList) {
        mTransactionList = transactionList;
        mOffersPresenter.getOffers();
    }

    @Override
    public void onNoTransaction() {
        mOffersPresenter.getOffers();
    }

    @Override
    public void onGetTransactionViewError() {

    }
}
