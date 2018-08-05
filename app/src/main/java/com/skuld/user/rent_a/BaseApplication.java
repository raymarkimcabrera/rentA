package com.skuld.user.rent_a;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class BaseApplication extends Application {


    private static android.app.Application mApp = null;

    @Override
    public void onCreate() {
        super.onCreate();

        mApp = this;
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

}
