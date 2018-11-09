package com.skuld.user.rent_a.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.model.transaction.Transaction;
import com.skuld.user.rent_a.presenter.TransactionPresenter;
import com.skuld.user.rent_a.utils.DateUtil;
import com.skuld.user.rent_a.utils.Preferences;
import com.skuld.user.rent_a.views.TransactionView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class ScheduleActivity extends BaseActivity implements TransactionView {

    @BindView(R.id.calendarView)
    CalendarView mCalendarView;

    private TransactionPresenter mTransactionPresenter;

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, ScheduleActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initializePresenter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTransactionPresenter.getPaidTransactions(Preferences.getString(mContext, Preferences.USER_ID));
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_schedule;
    }

    @Override
    public void onGetTransactionViewSuccess(List<Transaction> transactionList){
        List<EventDay> eventDayList = new ArrayList<>();

        for (Transaction transaction : transactionList){
            Calendar eventDate = DateUtil.convertDateToCalendar(transaction.getStartDate());
            EventDay eventDay = new EventDay(eventDate, R.drawable.event_circle);
            eventDayList.add(eventDay);
        }

        mCalendarView.setEvents(eventDayList);
        try {
            mCalendarView.setDate(Calendar.getInstance());
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNoTransaction() {

    }

    @Override
    public void onGetTransactionViewError() {

    }

    @Override
    public void onTransactionStatusUpdateSuccess(Transaction transaction) {

    }

    @Override
    public void onTransactionStatusUpdateError() {

    }

    @Override
    public void onGetTransaction(Transaction transaction) {

    }

    @Override
    public void onGetTransactionError() {

    }

    private void initializePresenter(){
        mTransactionPresenter = new TransactionPresenter(mContext, this);
    }
}
