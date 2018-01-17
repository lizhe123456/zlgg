package com.zlcm.zlgg.ui.release;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.MvpActivity;
import com.zlcm.zlgg.model.bean.ChargInfoBean;
import com.zlcm.zlgg.presenter.pay.PayPresenter;
import com.zlcm.zlgg.presenter.pay.contract.PayContract;
import com.zlcm.zlgg.ui.release.adapter.AddressListAdapter;
import com.zlcm.zlgg.view.ZlToast;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lizhe on 2018/1/2.
 * 类介绍：支付页面
 */

public class PayActivity extends MvpActivity<PayPresenter> implements PayContract.View {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.wx_checked)
    ImageView wxChecked;
    @BindView(R.id.ali_checked)
    ImageView aliChecked;
    @BindView(R.id.delivery_address)
    TextView deliveryAddress;
    @BindView(R.id.delivery_num)
    TextView deliveryNum;
    @BindView(R.id.delivery_address_list)
    RecyclerView deliveryAddressList;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.price)
    TextView price;
    //计费信息
    private ChargInfoBean bean;
    //支付方式 0 ali 1 wx
    private int type = 0;
    private AddressListAdapter mAdapter;
    private String url;
    private IWXAPI api;
    private String orderInfo;   // 订单信息

    @Override
    protected int setLayout() {
        return R.layout.activity_pay;
    }

    @Override
    protected void setData() {
        title.setText("支付中心");
        api = WXAPIFactory.createWXAPI(this,"");
        wxChecked.setImageResource(R.drawable.un_checked);
        aliChecked.setImageResource(R.drawable.checked);
        bean = (ChargInfoBean) getIntent().getSerializableExtra("charg");
        time.setText(bean.getDuration() + " 天");
        price.setText(bean.getPrice() + " 元");
        deliveryNum.setText(bean.getList().size()+"");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new AddressListAdapter(this);
        deliveryAddressList.setLayoutManager(linearLayoutManager);
        deliveryAddressList.setAdapter(mAdapter);
        mAdapter.addFirstDataSet(bean.getList());
    }


    @OnClick({R.id.img_lift,R.id.wx_checked, R.id.ali_checked, R.id.btn_pay,R.id.rl_delivery_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_lift:
                finish();
                break;
            case R.id.wx_checked:
                wxChecked.setImageResource(R.drawable.checked);
                aliChecked.setImageResource(R.drawable.un_checked);
                type = 1;
                break;
            case R.id.ali_checked:
                wxChecked.setImageResource(R.drawable.un_checked);
                aliChecked.setImageResource(R.drawable.checked);
                type = 0;
                break;
            case R.id.btn_pay:
                if (bean != null) {
                    mPresenter.pay(bean.getOrder_number(),type);
                }
                break;
            case R.id.rl_delivery_address:
                if (isShow(deliveryAddressList)){
                    deliveryAddressList.setVisibility(View.GONE);
                }else {
                    deliveryAddressList.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void stateError() {
        ZlToast.showText(this,"支付失败");
    }

    @Override
    public void paySate(Map payInfo) {
        //支付成功的回调
        ZlToast.showText(this,"支付成功，可在我的发布中查询");
        mActivity.finish();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    public void wechatpay(View view){
        //1.post信息到服务器
//        StringRequest request = new StringRequest(url, this, this);
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(request);
        //4.处理支付结果：成功、失败、取消
    }

    /**调用微信支付*/
    public void sendPayRequest() {
        
    }

}
