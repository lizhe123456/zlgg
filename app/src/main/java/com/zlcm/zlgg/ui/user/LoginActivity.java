package com.zlcm.zlgg.ui.user;

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
import com.zlcm.zlgg.model.bean.LoginBean;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.user.LoginPresenter;
import com.zlcm.zlgg.presenter.user.contract.LoginContract;
import com.zlcm.zlgg.ui.MainActivity;
import com.zlcm.zlgg.utils.MobileUtil;
import com.zlcm.zlgg.utils.SpUtil;
import com.zlcm.zlgg.view.ZlToast;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */

public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.img_lift)
    ImageView imgLift;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;

    @BindView(R.id.btnGetCode)
    TextView btnGetCode;

    private String mobile;
    private String code;
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
        return R.layout.activity_login;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setData() {
        String username = SpUtil.getString(mActivity,"username");
        if (username != null) {
            etName.setText(username);
            etName.setSelection(username.length());
        }
        title.setText("手机登录");
        imgLift.setImageResource(R.drawable.back);
    }

    @Override
    public void stateError() {

    }

    @Override
    public void getResponse(LoginBean bean) {
        if (bean == null){
            ZlToast.showText(mActivity,"登录失败");
        }else {
            SpUtil.putString(mActivity,"loginId",bean.getLoginId());
            SpUtil.putString(mActivity,"token",bean.getToken());
            SpUtil.putString(this, "avatar", bean.getAvatar());
            SpUtil.putString(this, "nickName", bean.getNickName());
            finish();
        }
    }

    @Override
    public void codeState(ZLResponse response) {
        if (response.getCode() != 200){
            ZlToast.showText(mActivity,"验证码获取失败");
        }else {
            handler.sendEmptyMessage(SENDSUCCESSFUL);
        }
    }

    @OnClick({R.id.btnGetCode, R.id.btnLogin,R.id.img_lift})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGetCode:
                if (avoidRepeatClick(view))
                    mobile = etName.getText().toString().trim();
                    if (!TextUtils.isEmpty(mobile)) {
                        if (MobileUtil.isMobile(mobile)) {
                            mPresenter.getCode(mobile);
                            isStartTimer();
                        }else {
                            ZlToast.showText(mActivity,"手机号格式有误");
                        }
                    }else {
                        ZlToast.showText(mActivity,"请输入手机号");
                    }
                break;
            case R.id.btnLogin:
                if (avoidRepeatClick(view))
                    mobile = etName.getText().toString().trim();
                    code = etCode.getText().toString().trim();
                    if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(code)){
                        if (code.length() == 6 && MobileUtil.isMobile(mobile)){
                            mPresenter.login(mobile,code);
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
                break;
            case R.id.img_lift:
                finish();
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
}
