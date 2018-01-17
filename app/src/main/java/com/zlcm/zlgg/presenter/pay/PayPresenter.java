package com.zlcm.zlgg.presenter.pay;

import android.app.Activity;
import android.content.Context;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zlcm.zlgg.app.Constants;
import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.bean.PayInfo;
import com.zlcm.zlgg.model.http.response.ZL2Response;
import com.zlcm.zlgg.presenter.pay.contract.PayContract;
import com.zlcm.zlgg.utils.RxUtil;
import org.reactivestreams.Publisher;
import java.util.Map;
import javax.inject.Inject;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by lizhe on 2018/1/7.
 * 类介绍：
 */

public class PayPresenter extends BasePresenterImpl<PayContract.View> implements PayContract.Presenter {

    DataManager dataManager;

    @Inject
    public PayPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void pay(int oid, final int type) {
        mView.loading("支付中...");
        addSubscribe(dataManager.fetchPayInfo(oid,type)
                .compose(RxUtil.<ZL2Response<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleZL2())
                .flatMap(new Function<String, Publisher<Map<String, String>>>() {
                    @Override
                    public Publisher<Map<String, String>> apply(@NonNull String s) throws Exception {
                        Map<String, String> result = null;
                        if (type == 0){
                            result = ali(s);
                        } else if (type == 1) {
                            wx(s);
                        }
                        final Map<String, String> finalResult = result;
                        return Flowable.create(new FlowableOnSubscribe<Map<String, String>>() {
                            @Override
                            public void subscribe(FlowableEmitter<Map<String, String>> emitter) throws Exception {
                                try {
                                    emitter.onNext(finalResult);
                                    emitter.onComplete();
                                } catch (Exception e) {
                                    emitter.onError(e);
                                }
                            }
                        }, BackpressureStrategy.BUFFER);
                    }
                })
                .compose(RxUtil.<Map<String,String>>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<Map<String,String>>(mView){
                    @Override
                    public void onNext(Map<String,String> json) {
                        super.onNext(json);
                        if (json == null) {
                            mView.paySate(json);
                        }
                    }
                })
        );
    }

    public void wx(String order){
        IWXAPI mWxApi = WXAPIFactory.createWXAPI((Context) mView, Constants.APP_IP_WX,true);
        mWxApi.registerApp(Constants.APP_IP_WX);
        Gson gson = new Gson();
        PayInfo payInfo = gson.fromJson(order, PayInfo.class);
        PayReq req = new PayReq();
        req.appId = payInfo.getAppid();
        req.partnerId = payInfo.getPartnerid();
        req.prepayId = payInfo.getPrepayid();
        req.nonceStr = payInfo.getNoncestr();
        req.timeStamp = payInfo.getTimestamp();
        req.packageValue = payInfo.getPackage();
        req.sign = payInfo.getSign();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        //3.调用微信支付sdk支付方法
        mWxApi.sendReq(req);
    }

    public Map<String, String> ali(String order){
        PayTask payTask = new PayTask((Activity) mView);
        return payTask.payV2(order,true);
    }


}
