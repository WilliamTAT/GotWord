package com.ninggc.gotword.application;

import android.app.Application;
import android.util.Log;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;

/**
 * Created by Ning on 12/5/2017 0005.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initNoHttp();
    }

    private void initNoHttp() {
        NoHttp.initialize(this);
        Logger.setDebug(true);
        Logger.setTag("NOHTTP_ATUO");
    }
}
