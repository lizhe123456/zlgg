package com.zlcm.zlgg.presenter.hot.contract;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;
import com.zlcm.zlgg.model.bean.AdvertBean;

import java.util.List;

/**
 * Created by lizhe on 2017/12/28.
 * 类介绍：
 */

public interface HotContract {

    interface View extends BaseView {
        void showHotInfo(List<AdvertBean> list);

        void showMore(List<AdvertBean> list);
    }

    interface Presenter extends BasePresenter<View>{
        void getHotInfo();
    }
}
