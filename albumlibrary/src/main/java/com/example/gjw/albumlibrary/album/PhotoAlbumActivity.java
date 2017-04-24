package com.example.gjw.albumlibrary.album;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gjw.albumlibrary.GAlbum;
import com.example.gjw.albumlibrary.adapter.PhotoAdapter;
import com.example.gjw.albumlibrary.entity.Images;
import com.example.gjw.albumlibrary.entity.SerMap;
import com.example.gjw.albumlibrary.interfaces.DataBus;
import com.example.gjw.albumlibrary.interfaces.OnRecyclerViewItemClickListener;
import com.example.gjw.albumlibrary.interfaces.OnResultDatasListener;
import com.example.gjw.albumlibrary.util.AlbumUtils;
import com.example.gjw.albumlibrary.widget.DividerGridItemDecoration;
import com.example.gjw.photoalbum.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by guojiawei on 2017/3/7.
 */

public class PhotoAlbumActivity extends AppCompatActivity {
    public final static int REQUEST_CODE_FLODER_NAME = 0;
    public final static int REQUEST_CODE_CAMERA = 1;
    public final static int RESULT_CODE_IMAGE_PATH = 2;

    public final static String DEFAULT_ALBUM = "Camera";
    public final static String BUNDLE_TAG_IMAGES = "IMAGES";
    public final static String BUNDLE_TAG_FLODER = "FLODER";
    public final static String BUNDLE_TAG_FLODER_NAME = "FLODER_NAME";

    private RelativeLayout rlayoutBar;
    private RecyclerView photoAlbumRecyclerview;
    private Map<String, List<Images>> mImagesData = null;
    private PhotoAdapter photoAlbumAdapter;
    private Uri photoUri;
    private TextView btFloder;
    private TextView btSave;
    static Handler handler = null;
    private ArrayList<Images> allSelectImages = new ArrayList<>();
    public OnResultDatasListener onResultDatasListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        bindView();
    }

    public void initView() {
        setContentView(R.layout.activity_photo_album);
        photoAlbumRecyclerview = (RecyclerView) findViewById(R.id.photo_album_recyclerview);
        btFloder = (TextView) findViewById(R.id.bt_floder);
        rlayoutBar = (RelativeLayout) findViewById(R.id.bar);
        btSave = (TextView) findViewById(R.id.bt_save);
    }

    public void bindView() {
        rlayoutBar.setBackgroundColor(GAlbum.statuBarColor);
        btFloder.setTextColor(GAlbum.statuBarTextColor);
        btSave.setTextColor(GAlbum.statuBarTextColor);

        GridLayoutManager gm = new GridLayoutManager(this, 3);
        photoAlbumRecyclerview.setLayoutManager(gm);
        photoAlbumRecyclerview.addItemDecoration(new DividerGridItemDecoration(this));
        photoAlbumAdapter = new PhotoAdapter(this);
        photoAlbumRecyclerview.setAdapter(photoAlbumAdapter);
        photoAlbumAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //点击第一个item是打开相机
                if (position == 0) {
                    photoUri = AlbumUtils.openCameraObtainPhoto((Activity) getContext(), REQUEST_CODE_CAMERA);
                }
            }
        });
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    mImagesData = (Map<String, List<Images>>) msg.obj;
                    photoAlbumAdapter.addItems(mImagesData.get(DEFAULT_ALBUM));
                }
            }
        };

        //开启线程查询本地所有图片信息
        new queryThread(this).start();

        btFloder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SerMap serMap = new SerMap();
                serMap.setMap(mImagesData);
                Intent intent = new Intent();
                intent.putExtra(BUNDLE_TAG_FLODER, serMap);
                intent.setClass(getContext(), FloderActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FLODER_NAME);
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allSelectImages.clear();
                allSelectImages.addAll(photoAlbumAdapter.getCheckedDatas());
                DataBus.getInstance().post(allSelectImages);
                finish();
            }
        });
    }


    final static class queryThread extends Thread {
        Map<String, List<Images>> datas = null;

        public queryThread(Context context) {
            datas = AlbumUtils.queryLocalImages(context);
        }

        @Override
        public void run() {
            if (datas != null) {
                Message msg = new Message();
                msg.obj = datas;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拍照返回的照片信息
        if (requestCode == REQUEST_CODE_CAMERA) {
            //如果data不为空怎从data获取图片信息
            if (data != null) {
                Images img = AlbumUtils.queryUriImagePath(getContext(), photoUri);
                allSelectImages.clear();
                allSelectImages.add(img);
                //返回一个图片实体集合
//                setResult(RESULT_CODE_IMAGE_PATH, new Intent().putExtra(BUNDLE_TAG_IMAGES, allSelectImages));
                DataBus.getInstance().post(allSelectImages);
                finish();
            } else {
                //如果data为空，则从缓存的地址获取图片信息
                if (photoUri != null) {
                    Images img = AlbumUtils.queryUriImagePath(getContext(), photoUri);
                    allSelectImages.clear();
                    allSelectImages.add(img);
                    //返回一个图片实体集合
//                    setResult(RESULT_CODE_IMAGE_PATH, new Intent().putExtra(BUNDLE_TAG_IMAGES, allSelectImages));
                    DataBus.getInstance().post(allSelectImages);
                    finish();
                }
            }

        }
        if (requestCode == REQUEST_CODE_FLODER_NAME) {
            if (data != null) {
                String floderName = data.getStringExtra(BUNDLE_TAG_FLODER_NAME);
                photoAlbumAdapter.addItems(mImagesData.get(floderName));
            }
        }
    }

    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImagesData.clear();
    }

}
