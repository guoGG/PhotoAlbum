package com.example.gjw.albumlibrary.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.gjw.albumlibrary.entity.Images;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by guojiawei on 2017/4/13.
 */

public class AlbumUtils {
    private final static String TIME_FORMAT = "yyyy_MM_dd_HH_mm_ss";

    /**
     * 打开相机拍照返回所拍照片uri地址
     *
     * @param context
     * @return
     */
    public static Uri openCameraObtainPhoto(Activity context, int RequestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(TIME_FORMAT);
        String filename = timeStampFormat.format(new Date());
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Media.TITLE, filename);
        Uri photoUri = context.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        context.startActivityForResult(intent, RequestCode);

        return photoUri;
    }


    /**
     * 去除集合重复元素方法，根据set特性强行踢除重复元素
     *
     * @param folderNames 文件夹名称集合
     * @return 返回一个没有重复元素的集合
     */
    public static List<String> RemoveDuplicate(List<String> folderNames) {
        HashSet<String> set = new HashSet<>(folderNames);
        folderNames.clear();
        folderNames.addAll(set);
        return folderNames;
    }

    /**
     * 根据uri获取图片真实路径
     *
     * @param context
     * @param uri
     * @return 真实路径
     */
    public static Images queryUriImagePath(Context context, Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(
                uri, null, null, null, null);
        Images imageEntity = new Images();
        while (cursor.moveToNext()) {
            //获取图片路径
            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            //所属文件夹名称
            String folderName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
            //获取图片的名称
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            imageEntity.setName(name);
            imageEntity.setFolderName(folderName);
            imageEntity.setPath(path);

        }
        cursor.close();
        return imageEntity;
    }

    /**
     * 查询手机本地所有图片
     *
     * @param context
     * @return k 文件夹名称 v 文件夹内容
     */
    public static Map<String, List<Images>> queryLocalImages(Context context) {
        Map<String, List<Images>> imagesMap = new HashMap<>();

        List<Images> images = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        List<String> folderNames = new ArrayList<>();
        Images imageEntity = null;
        while (cursor.moveToNext()) {
            imageEntity = new Images();
            //获取图片的名称
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            //获取图片的生成日期
            byte[] data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            //获取图片的详细信息
            String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
            //获取图片路径
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            //所属文件夹名称
            String folderName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
            //加入文件夹名称集合
            folderNames.add(folderName);
            //将查询的图片路径加入图片路径集合
            imageEntity.setName(name);
            imageEntity.setFolderName(folderName);
            imageEntity.setPath(path);
            images.add(imageEntity);
        }
        //关闭游标
        cursor.close();
        //去除重复文件名
        folderNames = AlbumUtils.RemoveDuplicate(folderNames);
        //根据文件名分类相册
        List<Images> tempList = null;
        for (int folderIndex = 0; folderIndex < folderNames.size(); folderIndex++) {
            tempList = new ArrayList<>();
            tempList.clear();
            for (int imageIndex = 0; imageIndex < images.size(); imageIndex++) {
                if (images.get(imageIndex).getFolderName().equals(folderNames.get(folderIndex))) {
                    tempList.add(images.get(imageIndex));
                }
            }
            Collections.reverse(tempList); // 倒序排列
            imagesMap.put(folderNames.get(folderIndex), tempList);
        }
        return imagesMap;
    }
}
