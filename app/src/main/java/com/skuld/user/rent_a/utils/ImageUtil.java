package com.skuld.user.rent_a.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.skuld.user.rent_a.R;

public class ImageUtil {

    public static void loadImageFromUrl(Context context, ImageView imageView, String url) {
        Glide.with(context)   // pass Context
                .load(url)// pass the image url
                .into(imageView);
    }

}
