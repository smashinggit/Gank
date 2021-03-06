package com.smashing.gank.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.smashing.gank.MyApplication;
import com.smashing.gank.R;
import com.smashing.gank.adapter.AdapterCommon;
import com.smashing.gank.bean.GanHuoBean;
import com.smashing.gank.common.SpaceItemDecoration;
import com.smashing.gank.common.base.BaseFragment;
import com.smashing.gank.common.utils.ScreenUtil;
import com.smashing.gank.mvp.contact.CommonContact;
import com.smashing.gank.mvp.presenter.FrontPresenter;
import com.smashing.gank.ui.CustomWebViewActivity;
import com.smashing.gank.ui.PicActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * author：chensen on 2016/11/30 15:43
 * desc： 安卓
 */
@SuppressLint("ValidFragment")
public class FrontFragment extends BaseFragment implements CommonContact.View {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.floatActionBtn)
    FloatingActionButton floatActionBtn;

    private FrontPresenter mPresenter;

    private AdapterCommon mAdapterAll;
    private ArrayList<GanHuoBean> mData = new ArrayList<>();


    public FrontFragment(Context mContext) {
        super(mContext);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_common;
    }

    @Override
    protected void init() {
        mPresenter = new FrontPresenter(this);
        mPresenter.getData();

        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getData();
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getInstance(), LinearLayoutManager.VERTICAL, false);
        SpaceItemDecoration itemDecoration = new SpaceItemDecoration(ScreenUtil.dip2px(mContext, 20));
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(itemDecoration);
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        mAdapterAll = new AdapterCommon(MyApplication.getInstance(), mData, recyclerview);
        recyclerview.setAdapter(mAdapterAll);

        mAdapterAll.setOnItemClickListener(new AdapterCommon.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mData.get(position).getType().equals("福利")) {
                    Intent intent = new Intent(mContext, PicActivity.class);
                    intent.putExtra("url", mData.get(position).getUrl());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, CustomWebViewActivity.class);
                    intent.putExtra("url", mData.get(position).getUrl());
                    intent.putExtra("title", mData.get(position).getType());
                    startActivity(intent);
                }

            }
        });

        mAdapterAll.setOnLoadMoreListener(new AdapterCommon.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getMoreData();
            }
        });

        floatActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerview.scrollToPosition(0);
            }
        });

    }

    @Override
    public void setTheme(int color) {
        floatActionBtn.setBackgroundTintList(ColorStateList.valueOf(color));
    }




    @Override
    public void showData(List<GanHuoBean> data) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        mData.clear();
        mData.addAll(data);
        mAdapterAll.notifyDataSetChanged();

    }

    @Override
    public void showMoreData(List<GanHuoBean> data) {
        mAdapterAll.loadCompleted();
        if (data != null && data.size() > 0) {
            mData.addAll(data);
            mAdapterAll.notifyItemRangeChanged(mData.size() - data.size(), data.size());

        } else {
            Toast.makeText(mContext, "没有更多数据了", 0).show();
        }

    }


    @Override
    public void showFail(String msg) {
        mAdapterAll.loadCompleted();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(mContext, msg, 0).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void DismissLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }
}
