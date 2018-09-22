package com.skuld.user.rent_a.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.adapter.TransactionsRecyclerViewAdapter;
import com.skuld.user.rent_a.model.transaction.Transaction;
import com.skuld.user.rent_a.presenter.TransactionPresenter;
import com.skuld.user.rent_a.utils.Preferences;
import com.skuld.user.rent_a.views.TransactionView;

import java.util.List;

import butterknife.BindView;

public class TransactionsHistoryActivity extends BaseActivity implements TransactionView {

    @BindView(R.id.transactionsRecyclerView)
    RecyclerView mTransactionsRecyclerView;


    private TransactionPresenter mTransactionPresenter;
    private TransactionsRecyclerViewAdapter mTransactionsRecyclerViewAdapter;

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, TransactionsHistoryActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        initPresenter();
        mTransactionPresenter.getUserTransactions(Preferences.getString(mContext, Preferences.USER_ID));
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_transactions_history;
    }

    private void initPresenter() {
        mTransactionPresenter = new TransactionPresenter(mContext, this);
    }

    @Override
    public void onGetTransactionViewSuccess(List<Transaction> transactionList) {
        mTransactionsRecyclerViewAdapter = new TransactionsRecyclerViewAdapter(mContext, transactionList, new TransactionsRecyclerViewAdapter.OnClickTransactionListener() {
            @Override
            public void OnTransactionSelected(Transaction transaction) {

            }
        });

        mTransactionsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mTransactionsRecyclerView.setLayoutManager(llm);
        mTransactionsRecyclerView.setAdapter(mTransactionsRecyclerViewAdapter);
        mTransactionsRecyclerViewAdapter.notifyDataSetChanged();

        Log.i("TRANSACTIONS", "onGetTransactionViewSuccess: " + mTransactionsRecyclerViewAdapter);
    }

    @Override
    public void onNoTransaction() {

    }

    @Override
    public void onGetTransactionViewError() {

    }


}
