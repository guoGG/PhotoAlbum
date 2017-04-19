package com.example.gjw.albumlibrary.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by guojiawei on 2017/3/8.
 */

public class GlideUtil {
    public static void loadUrlImage(Context context, String url, ImageView view) {
        Glide.with(context).load(url).into(view);
    }

    public static void loadUriImage(Context context, Uri uri, ImageView view) {
        Glide.with(context).load(uri).into(view);
    }
}
