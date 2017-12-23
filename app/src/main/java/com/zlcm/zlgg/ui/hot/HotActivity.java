package com.zlcm.zlgg.ui.hot;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.BaseActivity;
import butterknife.BindView;

/**
 * author：lizhe
 * time： 2017-12-18
 * 不积跬步,无以至千里.不积小流,无以成江河
 * 类介绍：热门
 */

public class HotActivity extends BaseActivity {

    @BindView(R.id.img_lift)
    ImageView imgLift;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @Override
    protected int setLayout() {
        return R.layout.activity_hot;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setData() {
        title.setText(R.string.hot);
    }

}
