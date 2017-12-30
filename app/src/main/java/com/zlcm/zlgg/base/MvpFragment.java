package com.zlcm.zlgg.base;


import com.zlcm.zlgg.app.App;
import com.zlcm.zlgg.di.component.DaggerFragmentComponent;
import com.zlcm.zlgg.di.component.FragmentComponent;
import com.zlcm.zlgg.di.module.FragmentModule;
import com.zlcm.zlgg.view.LoadingDialog;
import com.zlcm.zlgg.view.ZlToast;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * author：lizhe
 * time： 2017-08-23
 * 不积跬步,无以至千里.不积小流,无以成江河
 * 类介绍：
 */

public abstract class MvpFragment<T extends BasePresenter> extends BaseFrament implements BaseView{

    @Inject
    protected T mPresenter;

    protected LoadingDialog loadingDialog;

    protected FragmentComponent getFragmentComponent(){
        return DaggerFragmentComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    protected void initLoading(){
        loadingDialog = new LoadingDialog(getContext());
    }

    @Override
    protected void init() {
        initInject();
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
        initLoading();
    }

    protected void initEvent(){
        //订阅
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null){
            mPresenter.datachView();
        }
        //解除订阅
        EventBus.getDefault().unregister(this);
        super.onDestroyView();

    }

    protected abstract void initInject();



    @Override
    public void loading(String msg) {
        loadingDialog.setMessage(msg);
        loadingDialog.show();
    }

    @Override
    public void unLoading() {
        if (loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
    }

    @Override
    public void showErrorMsg(String msg) {
        ZlToast.showText(getActivity(),msg);
    }

}
