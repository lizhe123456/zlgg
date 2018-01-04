package com.zlcm.zlgg.di.component;

import android.app.Activity;

import com.zlcm.zlgg.di.module.ActivityModule;
import com.zlcm.zlgg.di.scope.ActivityScope;
import com.zlcm.zlgg.ui.hot.HotActivity;
import com.zlcm.zlgg.ui.periphery.PeripheryActivity;
import com.zlcm.zlgg.ui.periphery.activity.DeviceDetailsActivity;
import com.zlcm.zlgg.ui.release.DeliveryAddressActivity;
import com.zlcm.zlgg.ui.release.ReleaseActivity;
import com.zlcm.zlgg.ui.setting.SettingActivity;
import com.zlcm.zlgg.ui.user.LoginActivity;
import com.zlcm.zlgg.ui.user.activity.UpdatePhoneActivity;
import com.zlcm.zlgg.ui.user.activity.UserInfoActivity;

import dagger.Component;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(LoginActivity activity);

    void inject(UserInfoActivity activity);

    void inject(UpdatePhoneActivity activity);

    void inject(SettingActivity activity);

    void inject(HotActivity activity);

    void inject(PeripheryActivity activity);

    void inject(DeviceDetailsActivity activity);

    void inject(ReleaseActivity activity);

    void inject(DeliveryAddressActivity activity);

}
