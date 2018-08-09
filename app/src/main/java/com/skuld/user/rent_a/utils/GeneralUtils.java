package com.skuld.user.rent_a.utils;

import android.app.Activity;
import android.view.Window;

import com.skuld.user.rent_a.R;

public class GeneralUtils {

    public static void hideTitleBar(Activity activity){
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


}
