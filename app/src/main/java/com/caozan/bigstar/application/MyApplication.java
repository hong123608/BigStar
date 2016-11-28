package com.caozan.bigstar.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by caozan on 2016/11/13.
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static synchronized MyApplication getContext() {
        return (MyApplication)mContext;
    }

    public String getImgCachePath() {
        return "";
    }

}
