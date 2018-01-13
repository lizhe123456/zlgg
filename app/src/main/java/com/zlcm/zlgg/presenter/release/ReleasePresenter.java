package com.zlcm.zlgg.presenter.release;

import com.google.gson.Gson;
import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.bean.ChargInfoBean;
import com.zlcm.zlgg.model.bean.ChargingBean;
import com.zlcm.zlgg.model.http.response.ZL2Response;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.release.contract.ReleaseContract;
import com.zlcm.zlgg.utils.RxUtil;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by lizhe on 2018/1/4.
 * 类介绍：
 */

public class ReleasePresenter extends BasePresenterImpl<ReleaseContract.View> implements ReleaseContract.Presenter {

    DataManager dataManager;
    @Inject
    public ReleasePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void submit(List<Integer> devices, File file,String phone, String desc, String address, long duration) {
        mView.loading("提交中...");
        //创建RequwstBody对象
        Gson gson = new Gson();
        String body = gson.toJson(devices);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        addSubscribe(dataManager.fetchgetSubmitAdvertInfo(body,requestBody,phone,desc,address,duration)
                    .compose(RxUtil.<ZL2Response<ChargInfoBean>>rxSchedulerHelper())
                    .compose(RxUtil.<ChargInfoBean>handleZL2())
                    .subscribeWith(new CommonSubscriber<ChargInfoBean>(mView){
                        @Override
                        public void onNext(ChargInfoBean chargingBean) {
                            super.onNext(chargingBean);
                            mView.showContent(chargingBean);
                        }
                    })
        );
    }
}
