package com.zlcm.zlgg.utils;

import android.content.Context;
import android.view.WindowManager;

import com.zlcm.zlgg.app.App;

/**
 * Created by lizhe on 2017/12/26.
 * 类介绍：
 */

public class ScreenUtil {
    public static int sysWidth()
    {
        WindowManager wm = (WindowManager) App.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width/2;
    }
    public static int sysHeight()
    {
        WindowManager wm = (WindowManager) App.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height/2;
    }
}
