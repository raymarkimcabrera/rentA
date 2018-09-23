package com.skuld.user.rent_a.views;

import com.skuld.user.rent_a.model.transaction.Transaction;

import java.util.List;

public interface TransactionView {

    void onGetTransactionViewSuccess(List<Transaction> transactionList);

    void onNoTransaction();

    void onGetTransactionViewError();

    void onTransactionStatusUpdateSuccess();

    void onTransactionStatusUpdateError();
}
