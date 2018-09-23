package com.skuld.user.rent_a.views;

import com.skuld.user.rent_a.model.payment.Payment;

public interface PaymentView {

    void onGetPaymentSuccess(Payment payment);
    void onGetPaymentError();
}
