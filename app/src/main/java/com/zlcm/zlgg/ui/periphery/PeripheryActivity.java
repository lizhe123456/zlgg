package com.zlcm.zlgg.ui.periphery;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.app.Constants;
import com.zlcm.zlgg.base.MvpActivity;
import com.zlcm.zlgg.base.adapter.BaseAdapter;
import com.zlcm.zlgg.model.bean.PeripheryDeviceBean;
import com.zlcm.zlgg.presenter.periphery.PeripheryPresenter;
import com.zlcm.zlgg.presenter.periphery.contract.PeripheryContract;
import com.zlcm.zlgg.ui.periphery.activity.DeviceDetailsActivity;
import com.zlcm.zlgg.ui.periphery.adapter.PeripheryAdapter;
import com.zlcm.zlgg.view.ZlToast;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * author：lizhe
 * time： 2017-12-18
 * 不积跬步,无以至千里.不积小流,无以成江河
 * 类介绍：周边
 */

public class PeripheryActivity extends MvpActivity<PeripheryPresenter> implements PeripheryContract.View, OnRefreshListener {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private PeripheryAdapter mAdapter;
    private boolean isMore = true;

    @Override
    protected int setLayout() {
        return R.layout.activity_periphery;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setData() {
        title.setText(R.string.periphery);
        mAdapter = new PeripheryAdapter(this);
        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mPresenter.getPeriphery(Constants.loction.longitude, Constants.loction.latitue);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Object item, int position) {
                PeripheryDeviceBean.Device device = (PeripheryDeviceBean.Device) item;
                Intent intent = new Intent();
                intent.setClass(mActivity, DeviceDetailsActivity.class);
                intent.putExtra("did",device.getDid());
                intent.putExtra("address",device.getAddress());
                mActivity.startActivity(intent);
            }
        });
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
                            mPresenter.getPeriphery(Constants.loction.longitude, Constants.loction.latitue);
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

    @Override
    public void stateError() {

    }


    @Override
    public void showContent(List<PeripheryDeviceBean.Device> list) {
        mAdapter.addFirstDataSet(list);
    }

    @Override
    public void showMore(List<PeripheryDeviceBean.Device> list) {
        if (list != null && list.size() > 0) {
            mAdapter.addMoreDataSet(list);
        } else {
            ZlToast.showText(this, "已经没有更多信息了");
            isMore = false;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.getPeriphery(Constants.loction.longitude, Constants.loction.latitue);
    }


    @OnClick(R.id.img_lift)
    public void onViewClicked() {
        finish();
    }
}
