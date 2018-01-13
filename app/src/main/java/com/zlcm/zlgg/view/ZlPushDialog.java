package com.zlcm.zlgg.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.zlcm.zlgg.R;


/**
 * Created by lizhe on 2018/1/11.
 * 类介绍：
 */

public class ZlPushDialog extends Dialog {

    ImageView img;
    TextView tvTitle;
    TextView tvDesc;

    ImageView exit;

    private String title;
    private String content;
    private Bitmap image;


    public ZlPushDialog(@NonNull Context context) {
        super(context,R.style.ZlCustomDialog);
    }


    /**
     * 调用完Builder类的create()方法后显示该对话框的方法
     */
    @Override
    public void show() {
        super.show();
        show(this);
    }

    private void show(ZlPushDialog dialog){
        if (!TextUtils.isEmpty(dialog.content)){
            dialog.tvDesc.setText(dialog.content);
        }
        if (dialog.image != null){
            dialog.img.setImageBitmap(dialog.image);
//            GlideuUtil.loadImageView(mContext,dialog.image,dialog.img);
        }
        if (!TextUtils.isEmpty(dialog.title)){
            dialog.tvTitle.setText(dialog.title);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_dialog);
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        img = findViewById(R.id.img);
        tvTitle = findViewById(R.id.tv_title);
        tvDesc = findViewById(R.id.tv_desc);
        exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZlPushDialog.this.dismiss();
            }
        });
    }

    public static class Builder {
        private ZlPushDialog dialog;

        public Builder(Context context) {
            dialog = new ZlPushDialog(context);
        }

        /**
         * 设置对话框标题
         *
         * @param title
         */
        public Builder setTitle(String title) {
            dialog.title = title;
            return this;
        }

        /**
         * 设置对话框文本内容,如果调用了setView()方法，该项失效
         *
         * @param msg
         */
        public Builder setMessage(String msg) {
            dialog.content = msg;
            return this;
        }

        /**
         * 设置对话框图片
         *
         * @param bitmap
         * @return
         */
        public Builder setImage(Bitmap bitmap){
            dialog.image = bitmap;
            return this;
        }

        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public ZlPushDialog build() {
            return dialog;
        }

    }
}
