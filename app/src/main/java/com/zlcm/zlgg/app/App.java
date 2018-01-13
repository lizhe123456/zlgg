package com.zlcm.zlgg.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import com.bumptech.glide.Glide;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnicornImageLoader;
import com.qiyukf.unicorn.api.YSFOptions;
import com.zlcm.zlgg.di.component.AppComponent;
import com.zlcm.zlgg.di.component.DaggerAppComponent;
import com.zlcm.zlgg.di.module.AppModule;
import com.zlcm.zlgg.di.module.HttpModule;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

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
        Unicorn.init(this, Constants.QY_APP_KEY, options(), new UnicornImageLoader() {
            @Nullable
            @Override
            public Bitmap loadImageSync(String uri, int width, int height) {
                try {
                    return Glide.with(mContext)
                            .load(uri)
                            .asBitmap() //必须
                            .centerCrop()
                            .into(width, height)
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void loadImage(String uri, int width, int height, ImageLoaderListener listener) {

            }
        });
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

    private YSFOptions options() {
        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        return options;
    }
}
