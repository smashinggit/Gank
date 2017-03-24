package com.smashing.gank.mvp.contact;

import com.smashing.gank.bean.HttpResponse;
import com.smashing.gank.common.base.BasePresenter;
import com.smashing.gank.common.base.BaseView;

/**
 * author：chensen on 2016/12/6 09:06
 * desc：
 */

public class LeftMenuContact {

    public interface View extends BaseView {
        void showPic(HttpResponse data);

        void showWords(HttpResponse data);
    }

    public interface Presenter extends BasePresenter {
        void getPic();

        void getWords();
    }
}
