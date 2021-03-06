package com.zlcm.zlgg.ui.periphery.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.MvpActivity;
import com.zlcm.zlgg.base.adapter.BaseAdapter;
import com.zlcm.zlgg.model.bean.AdvertBean;
import com.zlcm.zlgg.presenter.periphery.PeripheryDetailsPresenter;
import com.zlcm.zlgg.presenter.periphery.contract.PeripheryDetailsContract;
import com.zlcm.zlgg.ui.hot.activity.AdvertDetailsActivity;
import com.zlcm.zlgg.ui.hot.adapter.HotAdapter;
import com.zlcm.zlgg.ui.release.ReleaseActivity;
import com.zlcm.zlgg.view.ZlToast;

import java.io.Serializable;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lizhe on 2017/12/30.
 * 类介绍：设配详情
 */

public class DeviceDetailsActivity extends MvpActivity<PeripheryDetailsPresenter> implements PeripheryDetailsContract.View, OnRefreshListener {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.confirm)
    TextView confirm;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.charging)
    TextView charging;
    @BindView(R.id.visitors_flowrate)
    TextView visitorsFlowrate;
    @BindView(R.id.hot)
    TextView hot;
    private HotAdapter mAdapter;
    private boolean isMore = true;
    private int did;

    @Override
    protected int setLayout() {
        return R.layout.activity_device_details;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setData() {
        did = getIntent().getIntExtra("did", 0);
        if (did != 0) {
            mPresenter.getPeripheryDetailsInfo(did);
        }
        title.setText("设配详情");
        confirm.setText("发布");
        mAdapter = new HotAdapter(this);
        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Object item, int position) {
                Intent intent = new Intent();
                intent.setClass(mActivity, AdvertDetailsActivity.class);
                intent.putExtra("advert", (Serializable) item);
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

    @OnClick({R.id.img_lift,R.id.confirm})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.confirm:
                Intent intent = new Intent();
                intent.setClass(this, ReleaseActivity.class);
                if (did != 0) {
                    intent.putExtra("did", did);
                }
                startActivity(intent);
                break;
            case R.id.img_lift:
                finish();
                break;
        }
    }

    @Override
    public void stateError() {

    }

    @Override
    public void showContent(List<AdvertBean> advertBeen) {
        mAdapter.addFirstDataSet(advertBeen);
    }

    @Override
    public void showMore(List<AdvertBean> list) {
        if (list != null && list.size() > 0) {
            mAdapter.addMoreDataSet(list);
        } else {
            ZlToast.showText(this, "已经没有更多信息了");
            isMore = false;
        }
    }

    @Override
    public void showHead(double charging, int hot, int visitorsFlowrate) {
        this.charging.setText(charging+"/小时");
        this.hot.setText(hot+"");
        this.visitorsFlowrate.setText(visitorsFlowrate+"/日");
        this.address.setText(getIntent().getStringExtra("address"));
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }

}
