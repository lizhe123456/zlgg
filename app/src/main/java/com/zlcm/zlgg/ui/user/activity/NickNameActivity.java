package com.zlcm.zlgg.ui.user.activity;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lizhe on 2017/12/23.
 * 类介绍：昵称修改
 */

public class NickNameActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.nick_name)
    EditText nickName;

    String name;
    public static final int NICKNAME_RESULT = 2;

    @Override
    protected int setLayout() {
        return R.layout.activity_nick_name;
    }

    @Override
    protected void init() {
        name = getIntent().getStringExtra("nickName");
        if (name != null) {
            nickName.setText(name);
            nickName.setSelection(name.length());
        }
        title.setText("修改昵称");
    }

    @Override
    protected void setData() {

    }

    @OnClick(R.id.img_lift)
    public void onViewClicked() {
        if (!nickName.getText().toString().trim().equals(name)){
            Intent intent = new Intent();
            intent.putExtra("text", nickName.getText().toString().trim());
            setResult(NICKNAME_RESULT,intent);
        }
        finish();
    }
}
