package com.smashing.gank.ui;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.smashing.gank.R;
import com.smashing.gank.common.base.BaseActivity;
import com.smashing.gank.common.http.ImageLoader;
import com.smashing.gank.common.utils.SharedPreferencesUtils;
import com.smashing.gank.common.utils.SystemUtils;
import com.smashing.gank.theme.ColorView;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;

/**
 * author：chensen on 2016/12/2 14:44
 * desc：
 */

public class PicActivity extends BaseActivity {
    @Bind(R.id.status_bar)
    ColorView mStatusBar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.photoview)
    PhotoView photoview;


    private String url;


    @Override
    public int getLayoutId() {
        return R.layout.activity_pic;
    }

    @Override
    protected void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = SystemUtils.getStatusHeight(this)+2;
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
        toolbar.setBackgroundColor(Color.parseColor(SharedPreferencesUtils.getString(mContext, "themeColor", "#3F51B5")));
        mStatusBar.setBackgroundColor(Color.parseColor(SharedPreferencesUtils.getString(mContext, "themeColor", "#3F51B5")));

        url = getIntent().getStringExtra("url");
        ImageLoader.load(mContext, url, photoview);
        setToolBar(toolbar, "妹纸");

    }
}
