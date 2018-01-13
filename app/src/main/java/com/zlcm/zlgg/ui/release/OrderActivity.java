package com.zlcm.zlgg.ui.release;

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
import com.zlcm.zlgg.model.bean.ChargInfoBean;
import com.zlcm.zlgg.presenter.release.OrderPresenter;
import com.zlcm.zlgg.presenter.release.contract.OrderContract;
import com.zlcm.zlgg.ui.release.adapter.OrderAdapter;
import com.zlcm.zlgg.view.ZlToast;
import com.zlcm.zlgg.widgets.RecyclerViewDecoration;

import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lizhe on 2018/1/7.
 * 类介绍：
 */

public class OrderActivity extends MvpActivity<OrderPresenter> implements OrderContract.View ,OnRefreshListener{


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private OrderAdapter mAdapter;
    private boolean isMore = true;

    @Override
    public void stateError() {

    }

    @Override
    public void showContent(List<ChargInfoBean> list) {
        mAdapter.addFirstDataSet(list);
    }

    @Override
    public void showMore(List<ChargInfoBean> list) {
        if (list != null && list.size() > 0) {
            mAdapter.addMoreDataSet(list);
        } else {
            ZlToast.showText(this, "已经没有更多信息了");
            isMore = false;
        }
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_order;
    }

    @Override
    protected void setData() {
        title.setText("我的发布");
        mPresenter.getOrderList();
        mAdapter = new OrderAdapter(this);
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
        recyclerView.addItemDecoration(new RecyclerViewDecoration(this,RecyclerViewDecoration.VERTICAL_LIST));

        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Object item, int position) {
                ChargInfoBean chargInfoBean = (ChargInfoBean) item;
                if (chargInfoBean.getOrderState() == 0){
                    //未支付
                    Intent intent = new Intent(mActivity,PayActivity.class);
                    intent.putExtra("charg",chargInfoBean);
                    startActivity(intent);
                }else {
                    if (chargInfoBean.getAdvertState() == 0){
                        //审核中
                    }else if (chargInfoBean.getAdvertState() == 1){
                        //审核成功，已发布

                    }else if (chargInfoBean.getAdvertState() == 2){
                        //审核失败
                    }
                }
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick(R.id.img_lift)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }
}
