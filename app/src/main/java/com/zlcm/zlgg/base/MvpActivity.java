package com.zlcm.zlgg.base;



import com.zlcm.zlgg.app.App;
import com.zlcm.zlgg.di.component.ActivityComponent;
import com.zlcm.zlgg.di.component.DaggerActivityComponent;
import com.zlcm.zlgg.di.module.ActivityModule;
import com.zlcm.zlgg.view.LoadingDialog;
import com.zlcm.zlgg.view.ZlToast;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public abstract class MvpActivity<T extends BasePresenter> extends BaseActivity implements BaseView{

    @Inject
    protected T mPresenter;

    protected LoadingDialog loadingDialog;

    protected ActivityComponent getActivityComponent(){
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    protected void initLoading(){
        loadingDialog = new LoadingDialog(this);
    }

    @Override
    protected void init() {
        initInject();
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
        initLoading();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null){
            mPresenter.datachView();
        }
        super.onDestroy();
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
        ZlToast.showText(mActivity,msg);
    }

}
