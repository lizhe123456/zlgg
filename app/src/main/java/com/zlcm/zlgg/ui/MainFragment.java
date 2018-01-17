package com.zlcm.zlgg.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.app.Constants;
import com.zlcm.zlgg.base.MvpFragment;
import com.zlcm.zlgg.lib.LocationTask;
import com.zlcm.zlgg.lib.MarkerUtil;
import com.zlcm.zlgg.lib.OnLocationGetListener;
import com.zlcm.zlgg.lib.OnRegecodeGetListenter;
import com.zlcm.zlgg.lib.PositionEntity;
import com.zlcm.zlgg.lib.RegeocodeTask;
import com.zlcm.zlgg.lib.RouteTask;
import com.zlcm.zlgg.model.bean.ChargingBean;
import com.zlcm.zlgg.model.bean.DeviceBean;
import com.zlcm.zlgg.model.bean.HomePageBean;
import com.zlcm.zlgg.overlay.WalkRouteOverlay;
import com.zlcm.zlgg.presenter.HomePageContract;
import com.zlcm.zlgg.presenter.HomePagePresenter;
import com.zlcm.zlgg.ui.release.ReleaseActivity;
import com.zlcm.zlgg.ui.user.LoginActivity;
import com.zlcm.zlgg.ui.user.activity.NameAuthActivity;
import com.zlcm.zlgg.utils.AMapUtil;
import com.zlcm.zlgg.utils.GlideuUtil;
import com.zlcm.zlgg.utils.LogUtil;
import com.zlcm.zlgg.utils.SpUtil;
import com.zlcm.zlgg.view.ZlCustomDialog;
import com.zlcm.zlgg.view.ZlCustomerServiceDialog;
import com.zlcm.zlgg.view.ZlPushDialog;
import com.zlcm.zlgg.view.ZlToast;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */

public class MainFragment extends MvpFragment<HomePagePresenter> implements HomePageContract.View
        , AMap.OnMapTouchListener, RouteSearch.OnRouteSearchListener, AMap.OnMapLoadedListener
        , AMap.OnCameraChangeListener, AMap.OnMapClickListener, AMap.InfoWindowAdapter
        , OnRegecodeGetListenter, OnLocationGetListener {

    public static final String TAG = "MvpFragment";

    @BindView(R.id.img_lift)
    ImageView imgLift;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_right1)
    ImageView imgRight1;
    @BindView(R.id.img_right2)
    ImageView imgRight2;
    @BindView(R.id.tv_btn_login)
    TextView tvBtnLogin;
    @BindView(R.id.rl_no_login)
    RelativeLayout rlNoLogin;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.map_all)
    LinearLayout mapAll;
    @BindView(R.id.ll_device_info)
    LinearLayout mLlDevice;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.charging)
    TextView charging;
    @BindView(R.id.household)
    TextView household;
    @BindView(R.id.visitors_flowrate)
    TextView visitorsFlowrate;
    @BindView(R.id.tv_device_release)
    TextView tvDeviceRelease;
    @BindView(R.id.banner)
    ImageView ivBanner;
    @BindView(R.id.head_portrait)
    LinearLayout ivHeadPortrait;

    private AMap aMap;
    private boolean isLogin = false;
    private RegeocodeTask mRegeocodeTask;
    //绘制点标记
    private Marker mPositionMark, mInitialMark, tempMark;//可移动、圆点、点击
    //初始坐标、移动记录坐标
    private LatLng mStartPosition, mRecordPositon;
    //默认添加一次
    private boolean mIsFirst = true;
    //就第一次显示位置
    private boolean mIsFirstShow = true;
    private boolean mMapShow = false;
    private LatLng initLocation;
    private ValueAnimator animator = null;//坐标动画
    private BitmapDescriptor initBitmap, moveBitmap, smallIdentificationBitmap, bigIdentificationBitmap;//定位圆点、可移动、所有标识（设配）
    private RouteSearch mRouteSearch;

    private WalkRouteResult mWalkRouteResult;
    private LatLonPoint mStartPoint = null;//起点，116.335891,39.942295
    private LatLonPoint mEndPoint = null;//终点，116.481288,39.995576
    private final int ROUTE_TYPE_WALK = 3;
    private boolean isClickIdentification = false;
    WalkRouteOverlay walkRouteOverlay;//路线
    private String[] time;
    private String distance;
    private ChargingBean chargingBean;
    private boolean isData = true;
    private LatLng oldAddress;
    private ZlPushDialog zlPushDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_main;
    }


    private void initBitmap() {
        initBitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker);
        moveBitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_loaction_start);
        smallIdentificationBitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.zlp);
        bigIdentificationBitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.zlp);
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        mRegeocodeTask = new RegeocodeTask(getContext());
        LocationTask.getInstance(getContext()).setOnLocationGetListener(this);
    }

    /**
     * 初始化地图控制器对象
     */
    private void initAMap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            mRouteSearch = new RouteSearch(getContext());
            mRouteSearch.setRouteSearchListener(this);
            aMap.getUiSettings().setZoomControlsEnabled(false);
            aMap.getUiSettings().setGestureScaleByMapCenter(true);

            aMap.setOnMapTouchListener(this);
            aMap.setOnMapLoadedListener(this);
            aMap.setOnCameraChangeListener(this);
            aMap.setOnMapClickListener(this);
            aMap.setOnMarkerClickListener(onMarkerClickListener);
            aMap.setInfoWindowAdapter(this);
        }
    }

    AMap.OnMarkerClickListener onMarkerClickListener = new AMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(final Marker marker) {
            Log.e(TAG, "点击的Marker");
            Log.e(TAG, marker.getPosition() + "");
            isClickIdentification = true;
            if (tempMark != null) {
                tempMark.setIcon(smallIdentificationBitmap);
                walkRouteOverlay.removeFromMap();
                tempMark = null;
            }
            startAnim(marker);
            setDeviceInfo(MarkerUtil.getDevice(marker).getDid());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);
                        tempMark = marker;
                        Log.e(TAG, mPositionMark.getPosition().latitude + "===" + mPositionMark.getPosition().longitude);
                        mStartPoint = new LatLonPoint(mRecordPositon.latitude, mRecordPositon.longitude);
                        mPositionMark.setPosition(mRecordPositon);
                        mEndPoint = new LatLonPoint(marker.getPosition().latitude, marker.getPosition().longitude);
                        marker.setIcon(bigIdentificationBitmap);
                        marker.setPosition(marker.getPosition());
                        searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WalkDefault);
//                        Intent intent = new Intent(MainActivity.this, RouteActivity.class);
//                        intent.putExtra("start_lat", mPositionMark.getPosition().latitude);
//                        intent.putExtra("start_lng", mPositionMark.getPosition().longitude);
//                        intent.putExtra("end_lat", marker.getPosition().latitude);
//                        intent.putExtra("end_lng", marker.getPosition().longitude);
//                        startActivity(intent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return true;
        }
    };

    private void setDeviceInfo(int did) {
        mPresenter.getChargingInfo(did);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setData() {
        imgLift.setImageResource(R.drawable.personal);
        title.setText(R.string.title_name);

        initLocation();
        initAMap();
        initBitmap();

    }


    @OnClick({R.id.img_lift, R.id.img_right1, R.id.img_right2, R.id.tv_btn_login, R.id.location, R.id.tv_release
            , R.id.customer_service,R.id.tv_device_release})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.img_lift:
                if (isLogin) {
                    ((MainActivity) getActivity()).openDrawerLayout();
                } else {
                    toLogin();
                }
                break;
            case R.id.img_right1:
                break;
            case R.id.img_right2:
                break;
            case R.id.tv_btn_login:
                toLogin();
                break;
            case R.id.location:
                clickRefresh();
                break;
            case R.id.tv_release:
                if (isLogin) {
                    intent.setClass(getContext(),ReleaseActivity.class);
                    startActivity(intent);
                } else {
                    toLogin();
                }
                break;
            case R.id.customer_service:
                ZlCustomerServiceDialog zlCustomerServiceDialog = new ZlCustomerServiceDialog(getContext());
                zlCustomerServiceDialog.show();
                break;
            case R.id.tv_device_release:

                if (isLogin){
                    switch (chargingBean.getAuthCode()){
                        case Constants.NAMEAUTH :
                            intent.setClass(getContext(), ReleaseActivity.class);
                            startActivity(intent);
                            break;
                        case Constants.STOREUTH :
                            intent.setClass(getContext(), ReleaseActivity.class);
                            startActivity(intent);
                            break;
                        default:
                            intent.setClass(getContext(), NameAuthActivity.class);
                            startActivity(intent);
                            break;
                    }
                }else {
                    toLogin();
                }
                break;
        }
    }

    private void toLogin() {
        Intent intent = new Intent();
        intent.setClass(getContext(), LoginActivity.class);
        startActivity(intent);
    }


    public void token(String token) {
        LogUtil.d("token",token);
        LogUtil.d("loginId",SpUtil.getString(getContext(),"loginId"));
        if (TextUtils.isEmpty(token)) {
            isLogin = false;
            rlNoLogin.setVisibility(View.VISIBLE);
            ((MainActivity)getActivity()).setDrawerLock(true);
        } else {
            isLogin = true;
            rlNoLogin.setVisibility(View.GONE);
            ((MainActivity)getActivity()).setDrawerLock(false);
        }

    }

    @Override
    public void stateError() {

    }

    @Override
    public void showContent(HomePageBean homePageBean) {
        if (isData) {
            setHead(homePageBean.getHean());
            setPushInfo(homePageBean.getPushInfo());
        }
        isData = false;
        setMainData(homePageBean.getDeviceList(), homePageBean.getLogo());

    }

    @Override
    public void showChargingInfo(ChargingBean bean) {
        chargingBean = bean;
        tvAddress.setText(bean.getAddress());
        charging.setText(bean.getCharging());
        household.setText(bean.getHousehold());
        visitorsFlowrate.setText(bean.getVisitorsFlowrate());
        if (isLogin){
            switch (bean.getAuthCode()){
                case Constants.NAMEAUTH :
                    tvDeviceRelease.setText("开始发布广告");
                    break;
                case Constants.STOREUTH :
                    tvDeviceRelease.setText("开始发布广告");
                    break;
                default:
                    tvDeviceRelease.setText("未实名去认证");
                    break;
            }
        }else {
            tvDeviceRelease.setText("立即登录发布广告");
        }
        mLlDevice.setVisibility(View.VISIBLE);
        if (isShow(ivHeadPortrait)){
            ivHeadPortrait.setVisibility(View.GONE);
        }
    }

    private void setHead(HomePageBean.HeadBean headBean) {
        if (headBean != null) {
            Glide.with(getContext()).load(headBean.getImg()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (resource == null) {
                        ivHeadPortrait.setVisibility(View.GONE);
                    } else {
                        ivBanner.setImageBitmap(resource);
                        ivHeadPortrait.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    private void setPushInfo(final HomePageBean.PushInfo pushInfo) {
        if (pushInfo != null) {
            Glide.with(getContext()).load(pushInfo.getImg()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    zlPushDialog = new ZlPushDialog.Builder(getContext())
                            .setImage(resource)
                            .setTitle(pushInfo.getTitle())
                            .setMessage(pushInfo.getDesc())
                            .build();
                    zlPushDialog.show();
                }
            });
        }
    }

    private void setMainData(List<DeviceBean> list, String logo) {
        MarkerUtil.removeMarkers();
        MarkerUtil.addMarkers(aMap, list, logo, getContext());
    }


    @Override
    public View getInfoWindow(Marker marker) {
        Log.e(TAG, "getInfoWindow");
        View infoWindow = getActivity().getLayoutInflater().inflate(
                R.layout.info_window, null);
        render(marker, infoWindow);
        return infoWindow;
    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
        TextView tv_time_info = (TextView) view.findViewById(R.id.tv_time_info);
        TextView tv_distance = (TextView) view.findViewById(R.id.tv_distance);
        tv_time.setText(time[0]);
        tv_time_info.setText(time[1]);
        tv_distance.setText(distance);
    }

    @Override
    public View getInfoContents(Marker marker) {
        Log.e(TAG, "getInfoContents");
        return null;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

        LogUtil.d(TAG, "onCameraChangeFinish" + cameraPosition.target);
        if (!isClickIdentification) {
            mRecordPositon = cameraPosition.target;
        }
        mStartPosition = cameraPosition.target;
        mRegeocodeTask.setOnRegecodeGetListener(this);
        mRegeocodeTask.search(mStartPosition.latitude, mStartPosition.longitude);
        if (mIsFirst) {

            //表面图标
//            iv_refresh.setVisibility(View.VISIBLE);
//            iv_scan_code.setVisibility(View.VISIBLE);
            createInitialPosition(cameraPosition.target.latitude, cameraPosition.target.longitude);
            createMovingPosition();
            mIsFirst = false;
        }
        if (mInitialMark != null) {
            mInitialMark.setToTop();
        }
        if (mPositionMark != null) {
            mPositionMark.setToTop();
            if (!isClickIdentification) {
                animMarker();
                if (mRecordPositon != null){
                    if (AMapUtil.GetDistance(oldAddress,cameraPosition.target) > 1 * 1000){
                        mPresenter.getHomePage(cameraPosition.target.longitude, cameraPosition.target.latitude);
                    }
                }
                if (isShow(mLlDevice)) {
                    mLlDevice.setVisibility(View.GONE);
                }
            }
        }
        oldAddress = cameraPosition.target;
    }

    private void animMarker() {
        if (animator != null) {
            animator.start();
            return;
        }
        animator = ValueAnimator.ofFloat(mMapView.getHeight() / 2, mMapView.getHeight() / 2 - 30);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(150);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                mPositionMark.setPositionByPixels(mMapView.getWidth() / 2, Math.round(value));
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mPositionMark.setIcon(moveBitmap);
            }
        });
        animator.start();
    }

    private void endAnim() {
        if (animator != null && animator.isRunning())
            animator.end();
    }

    /**
     * 创建初始位置图标
     */
    private void createInitialPosition(double lat, double lng) {
        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.setFlat(true);
        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(new LatLng(lat, lng));
        markerOptions.icon(initBitmap);
        mInitialMark = aMap.addMarker(markerOptions);
        mInitialMark.setClickable(false);
    }

    /**
     * 创建移动位置图标
     */
    private void createMovingPosition() {
        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.setFlat(true);
//        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(new LatLng(0, 0));
        markerOptions.icon(moveBitmap);
        mPositionMark = aMap.addMarker(markerOptions);
        mPositionMark.setPositionByPixels(mMapView.getWidth() / 2,
                mMapView.getHeight() / 2);
        mPositionMark.setClickable(false);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        clickMap();
    }

    private void clickMap() {
        clickInitInfo();
        if (mRecordPositon != null) {
            CameraUpdate cameraUpate = CameraUpdateFactory.newLatLngZoom(
                    mRecordPositon, 17f);
            aMap.animateCamera(cameraUpate);
        }
    }

    private void clickRefresh() {
        clickInitInfo();
        if (initLocation != null) {
            CameraUpdate cameraUpate = CameraUpdateFactory.newLatLngZoom(
                    initLocation, 17f);
            aMap.animateCamera(cameraUpate);
        }
    }

    private void clickInitInfo() {
        isClickIdentification = false;
        if (null != tempMark) {
            tempMark.setIcon(smallIdentificationBitmap);
            tempMark.hideInfoWindow();
            tempMark = null;
        }
        if (null != walkRouteOverlay) {
            walkRouteOverlay.removeFromMap();
        }
    }

    /**
     * 地图加载完成
     */
    @Override
    public void onMapLoaded() {
        mMapShow = true;
    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() >= 2) {
            aMap.getUiSettings().setScaleControlsEnabled(false);
        } else {
            aMap.getUiSettings().setScrollGesturesEnabled(true);
        }
    }


    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mWalkRouteResult = result;
                    final WalkPath walkPath = mWalkRouteResult.getPaths()
                            .get(0);
                    walkRouteOverlay = new WalkRouteOverlay(getContext(), aMap, walkPath, mWalkRouteResult.getStartPos(), mWalkRouteResult.getTargetPos());
                    walkRouteOverlay.removeFromMap();
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                    int dis = (int) walkPath.getDistance();
                    int dur = (int) walkPath.getDuration();
                    time = AMapUtil.getFriendlyTimeArray(dur);
                    distance = AMapUtil.getFriendlyLength(dis);
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    tempMark.setTitle(des);
                    tempMark.showInfoWindow();
                    Log.e(TAG, des);
                } else if (result != null && result.getPaths() == null) {
                    ZlToast.showText(getContext(), getResources().getString(R.string.no_result));
                }
            } else {
                ZlToast.showText(getContext(), getResources().getString(R.string.no_result));
            }
        } else {
            ZlToast.showerror(getContext(), errorCode);
        }
    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        if (mStartPoint == null) {
            ZlToast.showText(getContext(), "定位中，稍后再试...");
            return;
        }
        if (mEndPoint == null) {
            ZlToast.showText(getContext(), "终点未设置");
        }
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, mode);
            mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        }
    }

    /**
     * 点击动画
     *
     * @param marker
     */
    private void startAnim(Marker marker) {
        ScaleAnimation anim = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f);
        anim.setDuration(300);
        marker.setAnimation(anim);
        marker.startAnimation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MarkerUtil.removeMarkers();
        mMapView.onDestroy();
        LocationTask.getInstance(getContext()).onDestroy();
//        RouteTask.getInstance(getContext()).removeRouteCalculateListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        token(SpUtil.getString(getContext(), "token"));
//        if (!mIsFirst){
//            mPresenter.getHomePage(mRecordPositon.longitude,mRecordPositon.latitude);
//        }
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
        if (mInitialMark != null) {
            mInitialMark.setToTop();
        }
        if (mPositionMark != null) {
            mPositionMark.setToTop();
        }
        LocationTask.getInstance(getContext()).startLocate();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        LocationTask.getInstance(getContext()).stopLocate();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onRegecodeGet(PositionEntity entity) {
        Log.e(TAG, "onRegecodeGet" + entity.address);
        entity.latitue = mStartPosition.latitude;
        entity.longitude = mStartPosition.longitude;
        RouteTask.getInstance(getContext()).setStartPoint(entity);
        RouteTask.getInstance(getContext()).search();
        Log.e(TAG, "onRegecodeGet" + mStartPosition);
    }

    @Override
    public void onLocationGet(PositionEntity entity) {
        LogUtil.d(TAG, "onLocationGet" + entity.address);
        Constants.loction = entity;
        RouteTask.getInstance(getContext()).setStartPoint(entity);
        mStartPosition = new LatLng(entity.latitue, entity.longitude);
        if (mIsFirstShow && mMapShow) {
            CameraUpdate cameraUpate = CameraUpdateFactory.newLatLngZoom(
                    mStartPosition, 17);
            mPresenter.getHomePage(entity.longitude, entity.latitue);
            aMap.animateCamera(cameraUpate);
            mIsFirstShow = false;
        }
        mInitialMark.setPosition(mStartPosition);
        initLocation = mStartPosition;
        Log.e(TAG, "onLocationGet" + mStartPosition);
    }

}
