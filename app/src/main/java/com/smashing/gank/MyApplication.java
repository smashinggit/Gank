package com.smashing.gank;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.smashing.gank.common.BuildConfig;
import com.smashing.gank.common.utils.SharedPreferencesUtils;

/**
 * author：chensen on 2016/11/30 14:37
 * desc：
 */

public class MyApplication extends Application {
    public static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = getApplicationContext();
        Logger.init("tag")
                .methodOffset(2)
                .methodCount(2)
                .hideThreadInfo()
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);

        SharedPreferencesUtils.putString(instance,"themeColor","#2196f3");
    }

    public static Context getInstance() {
        return instance;
    }
}
