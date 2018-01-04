package com.zlcm.zlgg.ui.release;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.app.Constants;
import com.zlcm.zlgg.base.MvpActivity;
import com.zlcm.zlgg.model.bean.JsonBean;
import com.zlcm.zlgg.model.bean.PeripheryDeviceBean;
import com.zlcm.zlgg.presenter.release.DeliveryInfoPresenter;
import com.zlcm.zlgg.presenter.release.contract.DeliveryInfoContract;
import com.zlcm.zlgg.ui.release.adapter.DeliveryAddressAdapter;
import com.zlcm.zlgg.utils.GetJsonDataUtil;
import com.zlcm.zlgg.view.ZlToast;

import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lizhe on 2018/1/2.
 * 类介绍：投放地址选择页面
 */

public class DeliveryAddressActivity extends MvpActivity<DeliveryInfoPresenter> implements DeliveryInfoContract.View,OnRefreshListener{

    @BindView(R.id.img_lift)
    ImageView imgLift;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.confirm)
    TextView confirm;
    @BindView(R.id.province)
    TextView province;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private ArrayList<Integer> devices = new ArrayList<>();
    private OptionsPickerView mPickerView;
    private String strProvince;
    private String strCity;
    private String strArea;
    private DeliveryAddressAdapter mAdapter;
    private boolean isMore = true;

    @Override
    protected int setLayout() {
        return R.layout.activity_delivery_address;
    }


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setData() {
        title.setText("选择投放");
        confirm.setText("确定");
        devices = getIntent().getIntegerArrayListExtra("devices");
        strProvince = Constants.province;
        strCity = Constants.city;
        strArea = Constants.district;
        province.setText(strProvince);
        city.setText(strCity);
        area.setText(strArea);
        mAdapter = new DeliveryAddressAdapter(this);
        mPresenter.getDeliveryInfo(strProvince,strCity,strArea,devices);
        initJsonData();
        initPickerView();
        mAdapter.setOnCheckeedListener(new DeliveryAddressAdapter.OnCheckeedListener() {
            @Override
            public void onCheckeed(ArrayList<Integer> device) {
                devices = device;
            }
        });
        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        refresh.setOnRefreshListener(this);

        //上拉加载
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动，既是否向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int[] lastVisiblePositions = manager.findLastVisibleItemPositions(new int[manager.getSpanCount()]);
                    int lastVisiblePos = getMaxElem(lastVisiblePositions);
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部
                    if (lastVisiblePos == (totalItemCount - 1) && isSlidingToLast) {
                        //加载更多功能的代码
                        if (isMore) {
                            mPresenter.getMore();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                if (dy > 0) {
                    //大于0表示，正在向下滚动
                    isSlidingToLast = true;
                } else {
                    //小于等于0 表示停止或向上滚动
                    isSlidingToLast = false;
                }
            }
        });
    }

    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    private void initPickerView() {// 弹出选择器

        mPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                province.setText(options1Items.get(options1).getPickerViewText());
                city.setText(options2Items.get(options1).get(options2));
                area.setText(options3Items.get(options1).get(options2).get(options3));
                mPresenter.getDeliveryInfo(options1Items.get(options1).getPickerViewText(),options2Items.get(options1).get(options2),options3Items.get(options1).get(options2).get(options3),devices);
            }
        })

                .setTitleText("城市选择")
                .setCyclic(false, false, false)
                .setDividerColor(R.color.tv_ccc)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        mPickerView.setPicker(options1Items, options2Items, options3Items);//三级选择器
    }

    public ArrayList<JsonBean> parseData(String result) {
        //Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    @OnClick({R.id.img_lift, R.id.choice, R.id.confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_lift :
                finish();
                break;
            case R.id.choice :
                mPickerView.show();
                break;
            case R.id.confirm :
                Intent intent = new Intent();
                intent.putIntegerArrayListExtra("devices",devices);
                setResult(Activity.RESULT_OK,intent);
                break;
        }
    }

    @Override
    public void stateError() {

    }

    @Override
    public void showContent(List<PeripheryDeviceBean.Device> list) {
        for (int i = 0; i < list.size(); i++) {
            for (Integer d : devices) {
                if (list.get(i).getDid() == d){
                    list.get(i).setCheckeed(true);
                }
            }
        }
        mAdapter.addFirstDataSet(list);
    }

    @Override
    public void showMore(List<PeripheryDeviceBean.Device> list) {
        for (int i = 0; i < list.size(); i++) {
            for (Integer d : devices) {
                if (list.get(i).getDid() == d){
                    list.get(i).setCheckeed(true);
                }
            }
        }
        if (list != null && list.size() > 0) {
            mAdapter.addMoreDataSet(list);
        } else {
            ZlToast.showText(this, "已经没有更多信息了");
            isMore = false;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }
}
