package com.zlcm.zlgg.app;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.zlcm.zlgg.di.component.AppComponent;
import com.zlcm.zlgg.di.component.DaggerAppComponent;
import com.zlcm.zlgg.di.module.AppModule;
import com.zlcm.zlgg.di.module.HttpModule;
import com.zlcm.zlgg.service.LocationService;
import com.zlcm.zlgg.utils.LogUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */

public class App extends MultiDexApplication {
    private static App instance;
    private Context mContext;
    public AppComponent appComponent;
    private Set<Activity> allActivitySet;

    public Context getContext() {
        return mContext;
    }

    public static synchronized App getInstance() {
        if (instance == null){
            return new App();
        }else {
            return instance;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!Constants.IS_DEBUG){
            AppExceptionHandler.getInstance().setCrashHanler(this);
        }
        instance = this;
        mContext = getApplicationContext();
    }




    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(this);
        super.attachBaseContext(base);
    }

    public void addActivity(Activity activity){
        if (allActivitySet == null){
            allActivitySet = new HashSet<>();
        }
        allActivitySet.add(activity);
    }

    public void removeActivity(Activity activity){
        if (activity != null){
            allActivitySet.remove(activity);
        }
    }

    public AppComponent getAppComponent() {
        if (appComponent == null){
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }

    public void exitApp(){
        if (allActivitySet != null){
            synchronized (allActivitySet){
                for (Activity activity : allActivitySet){
                    activity.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
