package com.zlcm.zlgg.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.zlcm.zlgg.R;

/**
 * Created by lizhe on 2017/12/23.
 * 类介绍：自定义对话框
 */

public class ZlCustomDialog extends Dialog {

    TextView mTvTitle;
    TextView mTvContent;
    ImageView mIvimg;
    TextView mNegativeBtn;
    TextView mBtnSubmit;
    FrameLayout mFrameLayout;

    private String title;
    private String content;
    private Bitmap image;
    private String cancel;
    private String confirm;
    private int color;
    private View.OnClickListener onConfirmListener;
    private View.OnClickListener onCancelListener;
    private boolean isNegativeBtnShow = true;
    private View mView;
    private View view_xian;
    public LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_dialog);
        mTvTitle = findViewById(R.id.title);
        mTvContent = findViewById(R.id.content);
        mIvimg = findViewById(R.id.img);
        mNegativeBtn = findViewById(R.id.cancel);
        mBtnSubmit = findViewById(R.id.submit);
        mFrameLayout = findViewById(R.id.frame_context);
        view_xian = findViewById(R.id.view_xian);
    }

    /**
     * 调用完Builder类的create()方法后显示该对话框的方法
     */
    @Override
    public void show() {
        super.show();
        show(this);
    }

    private void show(ZlCustomDialog dialog){
        if (!TextUtils.isEmpty(dialog.title)){
            dialog.mTvTitle.setText(dialog.title);
        }
        if (dialog.image == null){
            dialog.mIvimg.setVisibility(View.GONE);
        }else {
            dialog.mIvimg.setVisibility(View.VISIBLE);
            dialog.mIvimg.setImageBitmap(dialog.image);
        }
        if (!TextUtils.isEmpty(dialog.content)){
            dialog.mTvContent.setVisibility(View.VISIBLE);
            dialog.mTvContent.setText(dialog.content);
        }else {
            dialog.mTvContent.setText(View.GONE);
        }
        if (!TextUtils.isEmpty(dialog.cancel)){
            dialog.mNegativeBtn.setText(dialog.cancel);
        }else {
            dialog.mNegativeBtn.setText("取消");
        }
        if (!TextUtils.isEmpty(dialog.cancel)){
            dialog.mNegativeBtn.setText(dialog.confirm);
        }else {
            dialog.mNegativeBtn.setText("确定");
        }
        if (mView != null){
            if (dialog.mFrameLayout.getChildCount() > 0){
                dialog.mFrameLayout.removeViewAt(0);
            }
            dialog.mFrameLayout.addView(mView);
            dialog.mFrameLayout.setVisibility(View.VISIBLE);
            dialog.mTvContent.setVisibility(View.GONE);
        }else {
            dialog.mFrameLayout.setVisibility(View.GONE);
            dialog.mTvContent.setVisibility(View.VISIBLE);
        }
        if (onConfirmListener != null){
            mBtnSubmit.setOnClickListener(dialog.onConfirmListener);
        }
        if (onCancelListener != null){
            mNegativeBtn.setOnClickListener(dialog.onCancelListener);
        }
        if (isNegativeBtnShow){
            mNegativeBtn.setVisibility(View.VISIBLE);
            view_xian.setVisibility(View.VISIBLE);
        }else {
            mNegativeBtn.setVisibility(View.INVISIBLE);
            view_xian.setVisibility(View.INVISIBLE);
        }
        if (color != 0){
            mNegativeBtn.setTextColor(getContext().getResources().getColor(color));
        }
    }

    private ZlCustomDialog(Context context) {
        super(context,R.style.ZlCustomDialog);
        inflater = LayoutInflater.from(context);
    }


    public static class Builder {

        private ZlCustomDialog dialog;

        public Builder(Context context) {
            dialog = new ZlCustomDialog(context);
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
         * 设置确认按钮的回调
         *
         * @param onClickListener
         */
        public Builder setPositiveButton(View.OnClickListener onClickListener) {
            dialog.onConfirmListener = onClickListener;
            return this;
        }

        /**
         * 设置确认按钮的回调
         *
         * @param btnText,onClickListener
         */
        public Builder setPositiveButton(String btnText, View.OnClickListener onClickListener) {
            dialog.confirm = btnText;
            dialog.onConfirmListener = onClickListener;
            return this;
        }

        /**
         * 设置取消按钮的回掉
         *
         * @param onClickListener
         */
        public Builder setNegativeButton(View.OnClickListener onClickListener) {
            dialog.onCancelListener = onClickListener;
            return this;
        }

        /**
         * 设置按钮颜色
         *
         * @param color
         * @return
         */
        public Builder setNegativeButtonColor(int color){
            dialog.color = color;
            return this;
        }

        /**
         * 设置取消按钮的回调
         *
         * @param btnText,onClickListener
         */
        public Builder setNegativeButton(String btnText, View.OnClickListener onClickListener) {
            dialog.cancel = btnText;
            dialog.onCancelListener = onClickListener;
            return this;
        }


        /**
         * 设置是否显示取消按钮，默认显示
         *
         * @param isNegativeBtnShow
         */
        public Builder setNegativeBtnShow(boolean isNegativeBtnShow) {
            dialog.isNegativeBtnShow = isNegativeBtnShow;
            return this;
        }

        /**
         * 设置自定义内容View
         *
         * @param view
         */
        public Builder setView(View view) {
            dialog.mView = view;
            return this;
        }

        /**
         * 设置自定义内容View
         *
         * @param view
         */
        public Builder setView(int view) {
            dialog.mView = dialog.inflater.inflate(view,null);
            return this;
        }


        /**
         * 设置该对话框能否被Cancel掉，默认可以
         *
         * @param cancelable
         */
        public Builder setCancelable(boolean cancelable) {
            dialog.setCancelable(cancelable);
            return this;
        }

        /**
         * 设置对话框被cancel对应的回调接口，cancel()方法被调用时才会回调该接口
         *
         * @param onCancelListener
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            dialog.setOnCancelListener(onCancelListener);
            return this;
        }

        /**
         * 设置对话框消失对应的回调接口，一切对话框消失都会回调该接口
         *
         * @param onDismissListener
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            dialog.setOnDismissListener(onDismissListener);
            return this;
        }

        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public ZlCustomDialog build() {
            return dialog;
        }

    }

}
