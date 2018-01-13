package com.zlcm.zlgg.utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.zlcm.zlgg.view.ZlToast;

/**
 * Created by lizhe on 2018/1/13.
 * 类介绍：跳转至第三方应用
 */

public class SkipUtil {
    /**
     * 跳转到微信
     */
    private void getWechatApi(Context context){
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ZlToast.showText(context,"检查到您手机没有安装微信，请安装后使用该功能");
        }
    }
}
