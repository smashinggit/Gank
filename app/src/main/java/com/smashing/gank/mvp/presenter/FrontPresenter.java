package com.smashing.gank.mvp.presenter;

import com.smashing.gank.bean.HttpResponse;
import com.smashing.gank.common.http.MySubscriber;
import com.smashing.gank.common.http.RetrofitHelper;
import com.smashing.gank.mvp.contact.CommonContact;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author：chensen on 2016/11/30 15:54
 * desc：全部
 */

public class FrontPresenter implements CommonContact.Presenter {
    private CommonContact.View mView;

    private int page = 1;
    private int size = 20;

    public FrontPresenter(CommonContact.View mView) {
        this.mView = mView;
    }

    @Override
    public void getData() {
        page = 1;
        RetrofitHelper.getInstance()
                .getFront(size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<HttpResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView != null) {

                            mView.showFail("获取数据失败");
                        }
                    }

                    @Override
                    public void onNext(HttpResponse httpResponse) {
                        super.onNext(httpResponse);
                        if (mView != null) {
                            if (!httpResponse.isError()) {
                                if (httpResponse.getResults() != null && httpResponse.getResults().size() > 0) {
                                    mView.showData(httpResponse.getResults());

                                } else {
                                    mView.showFail("暂无数据");
                                }
                            } else {
                                mView.showFail("获取数据失败");
                            }

                        }
                    }
                });


    }

    @Override
    public void getMoreData() {
        page++;
        RetrofitHelper.getInstance()
                .getFront(size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<HttpResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView != null) {

                            mView.showFail(e.toString());
                        }
                    }

                    @Override
                    public void onNext(HttpResponse httpResponse) {
                        super.onNext(httpResponse);
                        if (mView != null) {
                            mView.showMoreData(httpResponse.getResults());
                        }
                    }
                });

    }

    @Override
    public void detachView() {
        mView = null;

    }

}
