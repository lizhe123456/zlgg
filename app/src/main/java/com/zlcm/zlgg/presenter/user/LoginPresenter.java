package com.zlcm.zlgg.presenter.user;

import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.bean.LoginBean;
import com.zlcm.zlgg.model.http.exception.SysException;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.user.contract.LoginContract;
import com.zlcm.zlgg.utils.RxUtil;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter{

    private DataManager dataManager;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    @Override
    public void login(String mobile, String code) {
        mView.loading("正在登录...");
        addSubscribe(dataManager.fetchMobileCodeInfo(mobile,code)
                    .compose(RxUtil.<ZLResponse<LoginBean>>rxSchedulerHelper())
                    .compose(RxUtil.<LoginBean>handleZL())
                    .subscribeWith(new CommonSubscriber<LoginBean>(mView) {
                        @Override
                        public void onNext(LoginBean loginBean) {
                            mView.getResponse(loginBean);
                        }
                    })
        );
    }

    @Override
    public void getCode(String mobile) {
//        mView.loading("加载中...");
        addSubscribe(dataManager.fetchMobileCodeInfo(mobile)
                    .compose(RxUtil.<ZLResponse>rxSchedulerHelper())
                    .flatMap(new Function<ZLResponse, Flowable<ZLResponse>>() {
                        @Override
                        public Flowable<ZLResponse> apply(@NonNull final ZLResponse zlResponse) throws Exception {
                            if (zlResponse.getCode() == 200){
                                return Flowable.create(new FlowableOnSubscribe<ZLResponse>() {
                                    @Override
                                    public void subscribe(FlowableEmitter<ZLResponse> emitter) throws Exception {
                                        try {
                                            emitter.onNext(zlResponse);
                                            emitter.onComplete();
                                        } catch (Exception e) {
                                            emitter.onError(e);
                                        }
                                    }
                                }, BackpressureStrategy.BUFFER);
                            }else {
                                return Flowable.error(new SysException(zlResponse.getCode()));
                            }
                        }
                    })
                    .subscribeWith(new CommonSubscriber<ZLResponse>(mView) {
                        @Override
                        public void onNext(ZLResponse zlResponse) {
                            mView.codeState(zlResponse);
                        }
                })
        );
    }


}
