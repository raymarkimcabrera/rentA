package com.skuld.user.rent_a.views;

import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.skuld.user.rent_a.model.transaction.Transaction;

import java.util.List;

public interface TransactionView {

    void onGetTransactionViewSuccess(List<Transaction> transactionList);

    void onNoTransaction();

    void onGetTransactionViewError();

    void onTransactionStatusUpdateSuccess(Transaction transaction);

    void onTransactionStatusUpdateError();

    void onGetTransaction(Transaction transaction);

    void onGetTransactionError();
}
