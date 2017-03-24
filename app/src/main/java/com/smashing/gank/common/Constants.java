package com.smashing.gank.common;

import com.smashing.gank.MyApplication;

import java.io.File;

/**
 * author：chensen on 2016/11/25 14:22
 * desc：
 */

public class Constants {
    public static final String PATH_CACHE = "http-cache";
    public static final String PATH_IMG = MyApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "image";
}
