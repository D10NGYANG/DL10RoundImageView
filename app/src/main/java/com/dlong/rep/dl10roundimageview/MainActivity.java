package com.dlong.rep.dl10roundimageview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dlong.rep.dlroundimageview.DLRoundImageView;

public class MainActivity extends AppCompatActivity {

    private DLRoundImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (DLRoundImageView) findViewById(R.id.img);
        img.setBorderWidth(20);
        img.setBorderColor(Color.WHITE);
        img.setHasShadow(true);
        img.setShadowColor(Color.GRAY);
        img.setShadowRadius(30f);
    }
}
