package com.smashing.gank.mvp.contact;

import com.smashing.gank.bean.GanHuoBean;
import com.smashing.gank.common.base.BasePresenter;
import com.smashing.gank.common.base.BaseView;

import java.util.List;

/**
 * author：chensen on 2016/12/2 16:24
 * desc： 福利
 */

public class FuliContact {

    public interface View extends BaseView {
        void showData(List<GanHuoBean> data);

        void showMoreData(List<GanHuoBean> data);
    }

    public interface Presenter extends BasePresenter {
        void getData();
        void getMoreData();

    }
}
