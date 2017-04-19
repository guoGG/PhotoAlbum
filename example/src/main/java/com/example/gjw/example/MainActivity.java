package com.example.gjw.example;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.gjw.albumlibrary.GAlbum;
import com.example.gjw.albumlibrary.album.PhotoAlbumActivity;


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
                        .open();
            }
        });
    }
}
