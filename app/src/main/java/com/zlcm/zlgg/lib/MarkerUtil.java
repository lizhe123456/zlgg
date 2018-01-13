package com.zlcm.zlgg.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.app.App;
import com.zlcm.zlgg.model.bean.DeviceBean;
import com.zlcm.zlgg.utils.GlideuUtil;
import com.zlcm.zlgg.view.ZlToast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by lizhe on 2017/12/27.
 * 类介绍：
 */

public class MarkerUtil {

    private static ArrayList<Marker> markers = new ArrayList<>();
    private static List<DeviceBean> deviceBeanArrayList = new ArrayList<>();

    public static void addMarkers(AMap amap, List<DeviceBean> list, String logo, Context context){
        deviceBeanArrayList = list;
        if (list != null && list.size() > 0){
            for (DeviceBean bean : list) {
                BitmapDescriptor bitmapDescriptor;
                if (TextUtils.isEmpty(logo)) {
                    bitmapDescriptor = BitmapDescriptorFactory
                            .fromResource(R.drawable.zlp);
                }else {
                    try {
                        Bitmap myBitmap = Glide.with(context).load(logo).asBitmap().into(120,120).get();
                        bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(myBitmap);
                    } catch (InterruptedException e) {
                        bitmapDescriptor = BitmapDescriptorFactory
                                .fromResource(R.drawable.zlp);
                    } catch (ExecutionException e) {
                        bitmapDescriptor = BitmapDescriptorFactory
                                .fromResource(R.drawable.zlp);
                    }
                }
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(bitmapDescriptor);
                markerOptions.position(new LatLng(bean.getDlatitude(), bean.getDlongitude()));
                Marker marker = amap.addMarker(markerOptions);
                markers.add(marker);
            }
        }else {
            //空
            ZlToast.showText(context,"该地区暂未开放服务");
        }
    }

    public static DeviceBean getDevice(@Nullable Marker marker) {
        for (int i = 0; i < markers.size(); i++) {
            if (markers.get(i).equals(marker)){
                return deviceBeanArrayList.get(i);
            }
        }
        return null;
    }

    public static void setMarkers(ArrayList<Marker> markers) {
        MarkerUtil.markers = markers;
    }

    /**
     * 移除marker
     */
    public static void removeMarkers() {
        if (markers != null) {
            for (Marker marker : markers) {
                marker.remove();
                marker.destroy();
            }
            markers.clear();
        }
    }
}
