package com.zlcm.zlgg.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.*;

public class Base64Utils {
    public static String Bitmap2StrByBase64(String path){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            return null;
        }
        Bitmap bitmap= BitmapFactory.decodeStream(fis);
        if (bitmap == null){
            return null;
        }
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

}
