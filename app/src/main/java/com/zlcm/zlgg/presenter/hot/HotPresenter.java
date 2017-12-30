package com.zlcm.zlgg.presenter.hot;

import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.bean.AdvertBean;
import com.zlcm.zlgg.model.bean.HotBean;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.hot.contract.HotContract;
import com.zlcm.zlgg.utils.RxUtil;
import javax.inject.Inject;

/**
 * Created by lizhe on 2017/12/28.
 * 类介绍：
 */

public class HotPresenter extends BasePresenterImpl<HotContract.View> implements HotContract.Presenter{

    private DataManager dataManager;
    private int page;

    @Inject
    public HotPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getHotInfo() {
        if (page == 0) {
            mView.loading("加载中...");
        }
        addSubscribe(dataManager.fetchHotAdvertInfo(page,SIZE)
                .compose(RxUtil.<ZLResponse<HotBean>>rxSchedulerHelper())
                .compose(RxUtil.<HotBean>handleZL())
                .subscribeWith(new CommonSubscriber<HotBean>(mView){
                    @Override
                    public void onNext(HotBean bean) {
                        if (page == 0) {
                            mView.showHotInfo(bean.getHot());
                        }else {
                            mView.showMore(bean.getHot());
                        }
                        page++;
                        super.onNext(bean);
                    }
                })
        );
    }

    public void initPage() {
        this.page = 0;
    }
}
