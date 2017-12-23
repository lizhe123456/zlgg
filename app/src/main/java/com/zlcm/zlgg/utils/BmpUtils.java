package com.zlcm.zlgg.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by lizhe on 2017/12/23.
 * 类介绍：图片处理类
 */

public class BmpUtils {

    /**
     * @param
     * @param bytes
     * @param opts
     * @return Bitmap
     */
    public static Bitmap getPicFromBytes(byte[] bytes,
                                         BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                        opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

}
