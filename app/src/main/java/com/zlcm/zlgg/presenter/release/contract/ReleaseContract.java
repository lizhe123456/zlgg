package com.zlcm.zlgg.presenter.release.contract;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;
import com.zlcm.zlgg.model.bean.ChargInfoBean;
import java.io.File;
import java.util.List;

/**
 * Created by lizhe on 2018/1/4.
 * 类介绍：
 */

public interface ReleaseContract {

    interface View extends BaseView{
        void showContent(ChargInfoBean bean);
    }

    interface Presenter extends BasePresenter<View>{
        void submit(List<Integer> list, File file, String desc, String address, long duration);
    }
}
