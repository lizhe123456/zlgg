package com.zlcm.zlgg.ui.user.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.MvpActivity;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.user.UpdatePhonePresenter;
import com.zlcm.zlgg.presenter.user.contract.UpdatePhoneContract;
import com.zlcm.zlgg.utils.MobileUtil;
import com.zlcm.zlgg.utils.SpUtil;
import com.zlcm.zlgg.view.ZlToast;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lizhe on 2017/12/25.
 * 类介绍：
 */

public class UpdatePhoneActivity extends MvpActivity<UpdatePhonePresenter> implements UpdatePhoneContract.View {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.linearLayout1)
    LinearLayout linearLayout1;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.btnGetCode)
    TextView btnGetCode;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.btnLogin)
    TextView btnLogin;
    @BindView(R.id.newetName)
    EditText newetName;
    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;

    private boolean flag;
    private String mobile;
    private String code;
    public static final int RESULT = 6;
    private static final int TICK_TIME = 1;
    private static final int SENDSUCCESSFUL = 2;
    //验证码重发倒计时
    private int secondleft = 60;
    //The timer.
    private Timer timer;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TICK_TIME:
                    String getCodeAgain = getString(R.string.getcode_again);
                    String timerMessage = getString(R.string.timer_message);
                    secondleft--;
                    if (secondleft <= 0) {
                        timer.cancel();
                        btnGetCode.setEnabled(true);
                        btnGetCode.setText(getCodeAgain);
                    } else {
                        btnGetCode.setText(secondleft + timerMessage);
                    }
                    break;
                case SENDSUCCESSFUL:
                    etName.setEnabled(false);
                    break;
            }
        }
    };

    @Override
    protected int setLayout() {
        return R.layout.activity_update_phone;
    }

    @Override
    protected void setData() {
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.GONE);
        btnLogin.setText("下一步");
        title.setText("更换手机号");
    }


    @OnClick({R.id.img_lift, R.id.btnLogin,R.id.btnGetCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_lift:
                finish();
                break;
            case R.id.btnLogin:
                if (flag) {
                    mobile = newetName.getText().toString().trim();
                    code = etCode.getText().toString().trim();
                    if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(code)){
                        if (code.length() == 6 && MobileUtil.isMobile(mobile)){
                            if (!mobile.equals(SpUtil.getString(this, "username"))) {
                                mPresenter.provingPhone(mobile, code);
                            }else {
                                ZlToast.showText(this,"与原号码一致，无法更换");
                            }
                            SpUtil.putString(mActivity,"username",mobile);
                        }else {
                            if (code.length() != 6){
                                ZlToast.showText(mActivity,"验证码格式有误");
                            }else {
                                ZlToast.showText(mActivity,"手机号格式有误");
                            }
                        }
                    }else {
                        if (TextUtils.isEmpty(mobile)) {
                            ZlToast.showText(mActivity,"请输入手机号");
                        }else {
                            ZlToast.showText(mActivity,"请输入验证码");
                        }
                    }
                } else {
                    if (etName.getText().toString().trim().equals(SpUtil.getString(this, "username"))) {
                        linearLayout1.setVisibility(View.GONE);
                        linearLayout2.setVisibility(View.VISIBLE);
                        linearLayout3.setVisibility(View.VISIBLE);
                        btnLogin.setText("更换手机号");
                        flag = true;
                    }else {
                        ZlToast.showText(this,"输入错误，与原手机号不一致");
                    }
                }
                break;
            case R.id.btnGetCode:
                if (avoidRepeatClick(view))
                    mobile = newetName.getText().toString().trim();
                if (!TextUtils.isEmpty(mobile)) {
                    if (MobileUtil.isMobile(mobile)) {
                        mPresenter.getUpdateCode(mobile);
                        isStartTimer();
                    }else {
                        ZlToast.showText(mActivity,"手机号格式有误");
                    }
                }else {
                    ZlToast.showText(mActivity,"请输入手机号");
                }
                break;
        }
    }

    /**
     * 倒计时
     */
    public void isStartTimer() {
        btnGetCode.setEnabled(false);
        btnGetCode.setBackgroundResource(R.drawable.btn_getcode_shape_gray);
        secondleft = 60;
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(TICK_TIME);
            }
        }, 1000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void stateError() {

    }

    @Override
    public void codeState(ZLResponse response) {

    }

    @Override
    public void provingSate(ZLResponse response) {

    }
}
