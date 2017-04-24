package com.example.gjw.albumlibrary;

import android.content.Context;
import android.content.Intent;

import com.example.gjw.albumlibrary.album.PhotoAlbumActivity;
import com.example.gjw.albumlibrary.interfaces.DataBus;
import com.example.gjw.albumlibrary.interfaces.OnResultDatasListener;

/**
 * Created by guojiawei on 2017/4/18.
 */

public class GAlbum {

    private Context mContext;


    /**
     * 最大选择数
     */
    public static int maxSelectNum = 1;
    /**
     * 状态栏颜色
     */
    public static int statuBarColor = 0xFFFFFFFF;
    /**
     * 状态栏文字颜色
     */
    public static int statuBarTextColor = 0xFF000000;


    public GAlbum(Context context) {
        this.mContext = context;
    }


    public GAlbum open() {
        Intent intent = new Intent();
        intent.setClass(mContext, PhotoAlbumActivity.class);
        mContext.startActivity(intent);
        return this;
    }

    public GAlbum setMaxSelectNum(int maxSelectNum) {
        this.maxSelectNum = maxSelectNum;
        return this;
    }

    public GAlbum setStatuBarColor(int statuBarColor) {
        this.statuBarColor = statuBarColor;
        return this;
    }

    public GAlbum setStatuBarTextColor(int statuBarTextColor) {
        this.statuBarTextColor = statuBarTextColor;
        return this;
    }

    public GAlbum registerListener(OnResultDatasListener listener) {
        DataBus.getInstance().register(listener);
        return this;
    }
}
