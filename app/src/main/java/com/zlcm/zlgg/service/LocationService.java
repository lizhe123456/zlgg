package com.zlcm.zlgg.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zlcm.zlgg.event.PositionEntityEvent;
import com.zlcm.zlgg.lib.LocationTask;
import com.zlcm.zlgg.lib.OnLocationGetListener;
import com.zlcm.zlgg.lib.PositionEntity;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by lizhe on 2017/12/26.
 * 类介绍：定位服务，eventbus传递
 */

public class LocationService extends Service implements OnLocationGetListener{

    private LocationTask mLocationTask;

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationTask = LocationTask.getInstance(this);
        mLocationTask.setOnLocationGetListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mLocationTask.startLocate();
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mLocationTask.onDestroy();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationTask.onDestroy();
    }

    @Override
    public void onLocationGet(PositionEntity entity) {
        EventBus.getDefault().post(entity);
    }

}
