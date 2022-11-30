package com.seuittrv.dancingrobot3d;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private MyView mMyView = null;//a custom view for drawing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        getSupportActionBar().hide();//hide the title bar
        mMyView = new MyView(this);
        setContentView(mMyView);
    }
}
