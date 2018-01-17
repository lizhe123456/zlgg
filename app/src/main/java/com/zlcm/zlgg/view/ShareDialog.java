package com.zlcm.zlgg.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zlcm.zlgg.R;

/**
 * Created by lizhe on 2018/1/15.
 * 类介绍：
 */

public class ShareDialog extends Dialog {

    private LinearLayout qq_friend;
    private LinearLayout qq_space;
    private LinearLayout wx_friend;
    private LinearLayout circle_of_friends;
    private LinearLayout copy_link;
    private ImageView exit;
    private Context mContext;

    public ShareDialog(@NonNull Context context) {
        super(context,R.style.ZlCustomerServiceDialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_dialog);
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        exit = findViewById(R.id.exit);
        qq_friend = findViewById(R.id.qq_friend);
        qq_space = findViewById(R.id.qq_space);
        wx_friend = findViewById(R.id.wx_friend);
        circle_of_friends = findViewById(R.id.circle_of_friends);
        copy_link = findViewById(R.id.copy_link);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareDialog.this.dismiss();
            }
        });
    }


}
