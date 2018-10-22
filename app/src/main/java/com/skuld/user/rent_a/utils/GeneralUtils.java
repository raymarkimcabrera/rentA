package com.skuld.user.rent_a.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skuld.user.rent_a.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralUtils {

    public static void hideTitleBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public static void setDefaultRecyclerView(Context context, RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public static boolean isValidEmail(String emailStr) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static Date getCurrentDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();

        try {
            currentDate = simpleDateFormat.parse(calendar.getTime().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return currentDate;
    }

    public static String toPesoFormat(double peso) {
        return "₱" + new DecimalFormat("#,###,##0.00").format(peso);
    }
}
