package com.zlcm.zlgg.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import com.qiyukf.unicorn.api.Unicorn;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.ui.setting.activity.FeedBackActivity;

/**
 * Created by lizhe on 2018/1/13.
 * 类介绍：
 */

public class ZlCustomerServiceDialog extends Dialog{

    private LinearLayout repair;
    private LinearLayout customerService;
    private LinearLayout feedback;
    private LinearLayout all;
    private Context mContext;


    public ZlCustomerServiceDialog(Context context) {
        super(context,R.style.ZlCustomerServiceDialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_service_dialog);
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        repair = findViewById(R.id.repair);
        customerService = findViewById(R.id.customer_service);
        feedback = findViewById(R.id.feedback);
        all = findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZlCustomerServiceDialog.this.dismiss();
            }
        });
        repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZlCustomerServiceDialog.this.dismiss();
                ZlToast.showText(mContext,"暂未开放");
            }
        });
        customerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZlCustomerServiceDialog.this.dismiss();
                String title = "客服";
//                ConsultSource source = new ConsultSource(sourceUrl, sourceTitle, "custom information string");
                // 打开客服窗口
                Unicorn.openServiceActivity(mContext, title, null);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZlCustomerServiceDialog.this.dismiss();
                Intent intent = new Intent();
                intent.setClass(mContext, FeedBackActivity.class);
                mContext.startActivity(intent);
            }
        });

    }





}
