package com.zlcm.zlgg.di.component;

import android.app.Activity;

import com.zlcm.zlgg.di.module.FragmentModule;
import com.zlcm.zlgg.di.scope.FragmentScope;
import com.zlcm.zlgg.ui.MainFragment;
import com.zlcm.zlgg.ui.setting.SettingActivity;

import dagger.Component;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(MainFragment fragment);
}
