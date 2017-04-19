package com.example.gjw.albumlibrary.album;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.gjw.albumlibrary.adapter.FloderAdapter;
import com.example.gjw.albumlibrary.entity.SerMap;
import com.example.gjw.photoalbum.R;


/**
 * Created by guojiawei on 2017/4/14.
 */

public class FloderActivity extends AppCompatActivity {

    private RecyclerView photoFloderRecycler;
    private FloderAdapter floderAdapter;
    private SerMap datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_floder);
        initView();
        bindView();

    }

    private void initView() {
        datas = (SerMap) getIntent().getSerializableExtra(PhotoAlbumActivity.BUNDLE_TAG_FLODER);
        photoFloderRecycler = (RecyclerView) findViewById(R.id.photo_album_floder_recyclerview);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        photoFloderRecycler.setLayoutManager(lm);
    }

    private void bindView() {
        floderAdapter = new FloderAdapter(this);
        photoFloderRecycler.setAdapter(floderAdapter);
        floderAdapter.addItems(datas.getMap());
        floderAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent();
                intent.putExtra(PhotoAlbumActivity.BUNDLE_TAG_FLODER_NAME, floderAdapter.getFloderNames().get(position));
                setResult(PhotoAlbumActivity.REQUEST_CODE_FLODER_NAME, intent);
                finish();
            }
        });
    }

}
