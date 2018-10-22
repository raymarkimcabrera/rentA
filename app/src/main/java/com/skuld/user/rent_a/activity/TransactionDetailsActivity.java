package com.skuld.user.rent_a.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.model.payment.Payment;
import com.skuld.user.rent_a.model.transaction.Transaction;
import com.skuld.user.rent_a.presenter.PaymentPresenter;
import com.skuld.user.rent_a.presenter.TransactionPresenter;
import com.skuld.user.rent_a.views.PaymentView;
import com.skuld.user.rent_a.views.TransactionView;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TransactionDetailsActivity extends BaseActivity implements TransactionView, PaymentView {
    private static final String TAG = TransactionDetailsActivity.class.getSimpleName();

    @BindView(R.id.pickUpTextView)
    TextView mPickUpTextView;

    @BindView(R.id.destinationTextView)
    TextView mDestinationTextView;

    @BindView(R.id.pickUpDateTextView)
    TextView mPickUpDateTextView;

    @BindView(R.id.destinationDateTextView)
    TextView mDestinationDateTextView;

    @BindView(R.id.typeOfVehicleTextView)
    TextView mTypeOfVehicleTextView;

    @BindView(R.id.costTextView)
    TextView mCostTextView;

    @BindView(R.id.statusTextView)
    TextView mStatusTextView;

    @BindView(R.id.passengersTextView)
    TextView mPassengersTextView;

    @BindView(R.id.serialCodeTextView)
    TextView mSerialCodeTextView;

    private Transaction mTransaction;
    private Payment mPayment;
    private TransactionPresenter mTransactionPresenter;
    private PaymentPresenter mPaymentPresenter;

    public static Intent newIntent(Context context, Transaction transaction) {
        Intent intent = new Intent(context, TransactionDetailsActivity.class);
        intent.putExtra("TRANSACTION", transaction);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        getArgs();
        initToolbar();

        initializePresenter();
        Log.e(TAG, "onCreate: " + mTransaction.getPaymentID() );
        mPaymentPresenter.getPaymentByID(mTransaction.getPaymentID());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(TransactionsHistoryActivity.newIntent(mContext));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_transaction_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(TransactionsHistoryActivity.newIntent(mContext));
                finish();
                return true;
            case R.id.viewOffers:
                Intent intent = OffersActivity.newIntent(mContext, mTransaction);
                startActivity(intent);
                return true;
        }
        return false;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_transaction_details;
    }

    @Override
    public void onGetTransactionViewSuccess(List<Transaction> transactionList) {
        //Ignore
    }

    @Override
    public void onNoTransaction() {
        //Ignore
    }

    @Override
    public void onGetTransactionViewError() {
        //Ignore
    }

    @Override
    public void onTransactionStatusUpdateSuccess() {
        startActivity(TransactionsHistoryActivity.newIntent(mContext));
        finish();
    }

    @Override
    public void onTransactionStatusUpdateError() {
        Toast.makeText(mContext, "There is problem connecting to the server. PLease try again.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetPaymentSuccess(Payment payment) {
        mPayment = payment;
        initialize();
    }

    @Override
    public void onGetPaymentError() {

    }

    private void getArgs() {
        mTransaction = (Transaction) getIntent().getSerializableExtra("TRANSACTION");
//        Toast.makeText(mContext, mTransaction.getId(), Toast.LENGTH_SHORT).show();
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Transaction details");
        }
    }

    private void initialize() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa");
        mPickUpTextView.setSelected(true);
        mDestinationTextView.setSelected(true);
        mPickUpTextView.setText(mTransaction.getPickUpAddress());
        mDestinationTextView.setText(mTransaction.getDestinationAddress());
        mPassengersTextView.setText(String.valueOf(mTransaction.getPassengers()));
        mStatusTextView.setText(mTransaction.getStatus());
        mTypeOfVehicleTextView.setText(mTransaction.getTypeOfVehicle());
        mSerialCodeTextView.setText(mTransaction.getId());
        mPickUpDateTextView.setText(simpleDateFormat.format(mTransaction.getStartDate()));
        mDestinationDateTextView.setText(simpleDateFormat.format(mTransaction.getEndDate()));
        Log.e(TAG, "initialize: " + mPayment );
        mCostTextView.setText(String.valueOf(mPayment.getTotalAmount()));
    }

    private void initializePresenter() {
        mTransactionPresenter = new TransactionPresenter(mContext, this);
        mPaymentPresenter = new PaymentPresenter(mContext, this);
    }

    @OnClick(R.id.cancelBookButton)
    void onClick() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext)
                .setTitle("Cancel Transaction")
                .setMessage("Are you sure you want to cancel this transaction?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mTransactionPresenter.updateTransactionStatusById(mTransaction, "CANCELLED");
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

    }
}
