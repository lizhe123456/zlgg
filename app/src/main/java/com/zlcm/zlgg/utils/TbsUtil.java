package com.zlcm.zlgg.utils;

import android.content.Context;
import android.content.Intent;

import com.zlcm.zlgg.ui.TbsWebActivity;

/**
 * Created by lizhe on 2018/1/23.
 * 类介绍：
 */

public class TbsUtil {

    public static void toTbsWeb(Context context,String url){
        Intent intent = new Intent();
        intent.setClass(context, TbsWebActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

}
