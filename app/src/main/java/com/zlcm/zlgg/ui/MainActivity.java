package com.zlcm.zlgg.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiyukf.nimlib.sdk.NimIntent;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.app.App;
import com.zlcm.zlgg.base.BaseActivity;
import com.zlcm.zlgg.base.adapter.BaseAdapter;
import com.zlcm.zlgg.model.bean.MenuBean;
import com.zlcm.zlgg.ui.hot.HotActivity;
import com.zlcm.zlgg.ui.periphery.PeripheryActivity;
import com.zlcm.zlgg.ui.release.OrderActivity;
import com.zlcm.zlgg.ui.release.ReleaseActivity;
import com.zlcm.zlgg.ui.setting.SettingActivity;
import com.zlcm.zlgg.ui.user.activity.UserInfoActivity;
import com.zlcm.zlgg.ui.wallet.WalletActivity;
import com.zlcm.zlgg.utils.GlideuUtil;
import com.zlcm.zlgg.utils.SpUtil;
import com.zlcm.zlgg.view.RoundImageView;
import com.zlcm.zlgg.view.ZlToast;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fragment_context)
    FrameLayout fragmentContext;
    @BindView(R.id.navigation_menu)
    SwipeMenuRecyclerView navigationMenu;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    @BindView(R.id.head_portrait)
    RoundImageView headPortrait;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.navigation_all)
    RelativeLayout navigationAll;

    private long exitTime = 0;
    private MenuAdapter mAdapter;
    private List<MenuBean> mList = new ArrayList<>();
    private static final int HOT = 0;
    private static final int PERIPHERY = 1;
//    private static final int WALLET = 2;
    private static final int RELEASE = 2;
    private static final int SHARE = 3;
    private Fragment mContent;


    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        navigationMenu.setLayoutManager(layoutManager);
        mAdapter = new MenuAdapter(this);
        mList.add(new MenuBean(R.drawable.hot, "热门推荐"));
        mList.add(new MenuBean(R.drawable.periphery, "我的周围"));
//        mList.add(new MenuBean(R.drawable.pay, "我的钱包"));
        mList.add(new MenuBean(R.drawable.release_menu, "我的发布"));
        mList.add(new MenuBean(R.drawable.share, "邀请好友"));
        navigationMenu.setAdapter(mAdapter);
        mContent = new MainFragment();
    }

    @Override
    protected void setData() {
        mAdapter.addFirstDataSet(mList);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Object item, int position) {
                Intent intent = new Intent();
                switch (position) {
                    case HOT:
                        intent.setClass(mActivity, HotActivity.class);
                        startActivity(intent);
                        break;
                    case PERIPHERY:
                        intent.setClass(mActivity, PeripheryActivity.class);
                        startActivity(intent);
                        break;
//                    case WALLET:
//                        intent.setClass(mActivity, WalletActivity.class);
//                        startActivity(intent);
//                        break;
                    case RELEASE:
                        intent.setClass(mActivity, OrderActivity.class);
                        startActivity(intent);
                        break;
                    case SHARE:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        break;
                }
            }
        });

        //添加头部
//        View headerView = mInflater.inflate(R.layout.navigation_head, null);
//        navigationMenu.addHeaderView(headerView);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_context, mContent);
        fragmentTransaction.commit();
    }



    @OnClick({R.id.tv_setting, R.id.tv_customer_service, R.id.head_portrait})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_setting:
                intent.setClass(mActivity, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_customer_service:
                String title = "客服";
//                ConsultSource source = new ConsultSource(sourceUrl, sourceTitle, "custom information string");
                // 打开客服窗口
                Unicorn.openServiceActivity(this, title, null);
                    // 最好将intent清掉，以免从堆栈恢复时又打开客服窗口
                setIntent(new Intent());

                break;
            case R.id.head_portrait:
                intent.setClass(mActivity, UserInfoActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void openDrawerLayout() {
        drawerlayout.openDrawer(navigationAll);
    }

    public void closeDrawerLayout() {
        drawerlayout.closeDrawer(navigationAll);
    }

    public void setDrawerLock(boolean flag){
        if (flag){
            drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }else {
            drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    public void setAvatar(String path){
        GlideuUtil.loadImageView(this,path,headPortrait);
    }

    public void setUserName(String nickName){
        userName.setText(nickName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String avatar = SpUtil.getString(this,"avatar");
        String nickName = SpUtil.getString(this,"nickName");
        if (!TextUtils.isEmpty(avatar)) {
            setAvatar(avatar);
        }
        if (!TextUtils.isEmpty(nickName)){
            setUserName(nickName);
        }
    }

    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (drawerlayout.isDrawerOpen(navigationAll)) {
                closeDrawerLayout();
            } else {
                exit();
            }

            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ZlToast.showText(this, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            App.getInstance().exitApp();
        }
    }


}
