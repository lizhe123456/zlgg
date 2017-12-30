package com.zlcm.zlgg.overlay;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.PoiItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhe on 2017/12/26.
 * 类介绍：Poi图层
 */

public class PoiOverlay {
    private List<PoiItem> mPois;
    private AMap mAMap;
    private ArrayList<Marker> mPoiMarks = new ArrayList<Marker>();

    public PoiOverlay(List<PoiItem> mPois, AMap mAMap) {
        this.mPois = mPois;
        this.mAMap = mAMap;
    }

    public void addToMap() {
        for (int i = 0; i < mPois.size(); i++) {
            Marker marker = mAMap.addMarker(getMarkerOptions(i));
        }
    }
    /**
     * 去掉PoiOverlay上所有的Marker。
     */
    public void removeFromMap() {
        for (Marker mark : mPoiMarks) {
            mark.remove();
        }
    }

    /**
     * 移动镜头到当前的视角。
     */
    public void zoomToSpan() {
        try{
            if (mPois != null && mPois.size() > 0) {
                if (mAMap == null)
                    return;
                if(mPois.size()==1){
                    mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mPois.get(0).getLatLonPoint().getLatitude(),
                            mPois.get(0).getLatLonPoint().getLongitude()), 15f));
                }else{
                    LatLngBounds bounds = getLatLngBounds();
                    mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 5));
                }
            }
        }catch(Throwable e){
            e.printStackTrace();
        }
    }

    private LatLngBounds getLatLngBounds() {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < mPois.size(); i++) {
            b.include(new LatLng(mPois.get(i).getLatLonPoint().getLatitude(),
                    mPois.get(i).getLatLonPoint().getLongitude()));
        }
        return b.build();
    }

    private MarkerOptions getMarkerOptions(int index){
        return new MarkerOptions()
                .position(new LatLng(mPois.get(index).getLatLonPoint()
                        .getLatitude(), mPois.get(index)
                        .getLatLonPoint().getLongitude()))
                .title(getTitle(index)).snippet(getSnippet(index))
                .icon(getBitmapDescriptor(index));
    }

    /**
     * @param index
     * @return 给第几个Marker设置图标，并返回更换图标的图片。如不用默认图片，需要重写此方法。
     */
    private BitmapDescriptor getBitmapDescriptor(int index) {
        return null;
    }

    /**
     * @param index
     * @return 返回第index的Marker的标题。
     */
    private String getTitle(int index) {
        return mPois.get(index).getTitle();
    }

    /**
     * @param index
     * @return 回第index的Marker的详情。
     */
    private String getSnippet(int index) {
        return mPois.get(index).getSnippet();
    }

    /**
     * @param marker
     * @return 返回第index的poi的信息。
     */
    public int getPoiIndex(Marker marker){
        for (int i = 0; i < mPois.size(); i++) {
            if (mPoiMarks.get(i).equals(marker)){
                return i;
            }
        }
        return -1;
    }
}
