package com.zlcm.zlgg.widgets;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.BaseActivity;
import com.zlcm.zlgg.widgets.circle.ClipViewLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.OnClick;

public class ClipPictureActivity extends BaseActivity{
    @BindView(R.id.img_lift)
    ImageView imgLift;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.confirm)
    TextView confirm;
    @BindView(R.id.clip_view)
    ClipViewLayout clipview;
    @BindView(R.id.clip_view1)
    ClipViewLayout clip_view1;


    private String path;

    private int type;

    @Override
    protected int setLayout() {
        return R.layout.activity_clip_picture;
    }

    @Override
    protected void init() {
        title.setText("裁剪图片");
        confirm.setText("保存");
        path = getIntent().getStringExtra("path");
        type = getIntent().getIntExtra("clipType",0);
        if (type == 1){
            if (path != null) {
                clipview.setImageSrc(path);
                confirm.setVisibility(View.VISIBLE);
                clipview.setVisibility(View.VISIBLE);
                clip_view1.setVisibility(View.GONE);
            }
        }else {
            if (path != null) {
                clip_view1.setImageSrc(path);
                confirm.setVisibility(View.VISIBLE);
                clip_view1.setVisibility(View.VISIBLE);
                clipview.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void setData() {

    }

    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndReturn() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap;
        if (type == 1) {
            zoomedCropBitmap = clipview.clip();
        } else {
            zoomedCropBitmap = clip_view1.clip();
        }
        if (zoomedCropBitmap == null) {
            Log.e("android", "zoomedCropBitmap == null");
            return;
        }
        File file = new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg");
        if (file != null) {
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
//                getContentResolver().openOutputStream(file);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + file, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent();
            intent.putExtra("bitmap",file.getAbsolutePath());
            setResult(RESULT_OK, intent);
            finish();
        }
    }


    @OnClick({R.id.img_lift, R.id.confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_lift:
                finish();
                break;
            case R.id.confirm:
                generateUriAndReturn();
                break;
        }
    }
}