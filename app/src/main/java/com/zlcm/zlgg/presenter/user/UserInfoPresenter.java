package com.zlcm.zlgg.presenter.user;

import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.bean.UserInfoBean;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.user.contract.UserInfoContract;
import com.zlcm.zlgg.utils.RxUtil;

import java.io.File;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by lizhe on 2017/12/22.
 * 类介绍：用户信息presenter
 */

public class UserInfoPresenter extends BasePresenterImpl<UserInfoContract.View>
        implements UserInfoContract.Presenter{

    DataManager dataManager;

    @Inject
    public UserInfoPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void updateAvatar(File file, String nickName) {
        mView.loading("正在上传...");
        //创建RequwstBody对象
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        //创建nickNameBody对象
        RequestBody nickNameBody = RequestBody.create(MediaType.parse("text/plain"), nickName);

        addSubscribe(dataManager.fetchUploadFile(requestBody,nickNameBody)
                    .compose(RxUtil.<ZLResponse>rxSchedulerHelper())
                    .compose(RxUtil.handleZLState())
                    .subscribeWith(new CommonSubscriber<ZLResponse>(mView){
                        @Override
                        public void onNext(ZLResponse zlResponse) {
                            super.onNext(zlResponse);
                            mView.upload();
                        }
                    })
        );
    }


    @Override
    public void authen() {

    }

    @Override
    public void getUserInfo(String username) {
        mView.loading("加载中");
        addSubscribe(dataManager.fetchgetUserInfo(username)
                    .compose(RxUtil.<ZLResponse<UserInfoBean>>rxSchedulerHelper())
                    .compose(RxUtil.<UserInfoBean>handleZL())
                    .subscribeWith(new CommonSubscriber<UserInfoBean>(mView){
                        @Override
                        public void onNext(UserInfoBean bean) {
                            super.onNext(bean);
                            mView.showContent(bean);
                        }
                    })
        );
    }
}
