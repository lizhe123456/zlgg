package com.zlcm.zlgg.widgets;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by lizhe on 2017/12/30.
 * 类介绍：Banner图片加载器
 */

public class GlideImageLoader extends ImageLoader{

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(path).into(imageView);
    }


}
