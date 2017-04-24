package com.example.gjw.example;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.gjw.albumlibrary.GAlbum;
import com.example.gjw.albumlibrary.entity.Images;
import com.example.gjw.albumlibrary.interfaces.OnResultDatasListener;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GAlbum(MainActivity.this)
                        .setStatuBarColor(Color.BLUE)
                        .setStatuBarTextColor(Color.WHITE)
                        .registerListener(new OnResultDatasListener() {
                            @Override
                            public void onResultDatas(List<Images> datas) {
                                for (int i = 0; i < datas.size(); i++) {
                                    Log.e("haha", "haha" + datas.get(i).getPath());
                                }
                            }
                        })
                        .open();
            }
        });
    }

}
