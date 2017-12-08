package com.ninggc.gotword.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

/**
 * Created by Ning on 12/5/2017 0005.
 */

public class BaseActivity extends AppCompatActivity {
    protected Gson gson = new Gson();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    protected void initData() {

    }

    protected void initView() {

    }
}
