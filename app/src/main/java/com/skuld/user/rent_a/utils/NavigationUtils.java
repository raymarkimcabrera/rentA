package com.skuld.user.rent_a.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.FileProvider;

public class NavigationUtils {

    private static final String TAG = NavigationUtils.class.getSimpleName();

    public static void startActivity(Context context, Class clazz) {
        startActivity(context, createIntent(context, clazz));
    }

    public static void startActivity(Context context, Class clazz, int flags) {
        Intent intent = createIntentWithFlags(context, clazz, flags);
        startActivity(context, intent);
    }

    @NonNull
    public static Intent createIntentWithFlags(Context context, Class clazz, int flags) {
        Intent intent = createIntent(context, clazz);
        intent.setFlags(flags);
        return intent;
    }

    @NonNull
    public static Intent createIntent(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
    }


    public static void startActivityWithParent(Context context, Class clazz) {
        TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(createIntent(context, clazz))
                .startActivities();
    }

    public static void startCameraActivity(Activity activity, Uri fileUri, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(activity, intent, requestCode);
    }

    public static void startGalleryActivity(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(activity, Intent.createChooser(intent, "Select Picture"), requestCode);
    }

    public static void startGalleryActivityKitkat(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/jpeg");
        startActivityForResult(activity, intent, requestCode);
    }

}
