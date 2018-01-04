package com.zlcm.zlgg.ui.release;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.MvpActivity;
import com.zlcm.zlgg.model.bean.ChargInfoBean;
import com.zlcm.zlgg.presenter.release.ReleasePresenter;
import com.zlcm.zlgg.presenter.release.contract.ReleaseContract;
import com.zlcm.zlgg.view.ZlToast;
import com.zlcm.zlgg.widgets.PhotoListActivity;
import java.io.File;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * author：lizhe
 * time： 2017-12-18
 * 不积跬步,无以至千里.不积小流,无以成江河
 * 类介绍：发布页面
 */

public class ReleaseActivity extends MvpActivity<ReleasePresenter> implements ReleaseContract.View,View.OnTouchListener{

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.item_phone)
    TextView itemPhone;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.item_address)
    TextView itemAddress;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_time)
    EditText etTime;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.iv_go)
    ImageView ivGo;
    @BindView(R.id.delivery_advert)
    RelativeLayout deliveryAdvert;
    @BindView(R.id.advert_img)
    ImageView advertImg;
    @BindView(R.id.bmp)
    ImageView bmp;
    @BindView(R.id.release)
    TextView release;

    private final int RELEASE_PHOTO = 2;
    private final int RELEASE_DELIVERY = 3;
    private File bitmap;
    private ArrayList<Integer> devices = new ArrayList<>();

    @Override
    protected int setLayout() {
        return R.layout.activity_release;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setData() {
        title.setText("广告编辑");
        etDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 输入前的监听

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
                String content = etDesc.getText().toString();
                num.setText(content.length() + "/"
                        + 500);
            }
        });
    }





    @OnClick({R.id.img_lift, R.id.advert_img, R.id.bmp,R.id.delivery_advert,R.id.release})
    public void onViewClicked(View view) {

        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.img_lift:
                finish();
                break;
            case R.id.advert_img:
                //到相机相册选择,返回时上传
                intent.setClass(this, PhotoListActivity.class);
                startActivityForResult(intent, RELEASE_PHOTO);
                break;
            case R.id.bmp:
                intent.setClass(this, PhotoListActivity.class);
                startActivityForResult(intent, RELEASE_PHOTO);
                break;
            case R.id.delivery_advert:
                intent.setClass(this, DeliveryAddressActivity.class);
                intent.putIntegerArrayListExtra("devices",devices);
                startActivityForResult(intent,RELEASE_DELIVERY);
                break;
            case R.id.release:
                if (TextUtils.isEmpty(etTime.getText().toString().trim())){
                    ZlToast.showText(this,"请输入时间");
                    return;
                }
                long data = Integer.valueOf(etTime.getText().toString())*1000*60*60*24;
                if (data == 0){
                    ZlToast.showText(this,"时间不可为0");
                    return;
                }
                if (devices.size() == 0){
                    ZlToast.showText(this,"投放地址为空");
                    return;
                }
                String address = etAddress.getText().toString().trim();
                if (TextUtils.isEmpty(address)){
                    ZlToast.showText(this,"请填写广告活动所在地");
                    return;
                }
                String desc = etDesc.getText().toString();
                if (TextUtils.isEmpty(desc)){
                    ZlToast.showText(this,"请写一段优美的介绍");
                    return;
                }
                if (bitmap == null){
                    ZlToast.showText(this,"请选择您准备好的一张海报");
                    return;
                }
                mPresenter.submit(devices,bitmap,desc,address,data);
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
        if ((view.getId() == R.id.et_desc && canVerticalScroll(etDesc))) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return false;
    }

    /**
     * EditText竖直方向是否可以滚动
     * @param editText  需要判断的EditText
     * @return  true：可以滚动   false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case RELEASE_PHOTO :
                    String bmp = data.getStringExtra("bitmap");
                    if (!TextUtils.isEmpty(bmp)) {
                        bitmap = new File(bmp);
                    }
                    break;
                case RELEASE_DELIVERY:
                    devices = data.getIntegerArrayListExtra("devices");
                    tvNum.setText(devices.size()+"");
                    break;
            }
        }

    }

    @Override
    public void stateError() {

    }

    @Override
    public void showContent(ChargInfoBean bean) {
        Intent intent = new Intent();
        intent.setClass(this,PayActivity.class);
        intent.putExtra("charg",bean);
        startActivity(intent);
    }


}
