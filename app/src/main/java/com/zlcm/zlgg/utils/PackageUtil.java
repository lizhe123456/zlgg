package com.zlcm.zlgg.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.net.URI;
import java.util.UUID;

/**
 * author：lizhe
 * time： 2017-12-18
 * 不积跬步,无以至千里.不积小流,无以成江河
 * 类介绍：本应用相关公共方法
 */

public class PackageUtil {

    private Uri imageUri;

    public static final int CODE_RESULT_REQUEST = 101;
    //裁剪
    public static final int CODE_CAMERA_REQUEST = 102;
    //相册
    public static final int CODE_GALLERY_REQUEST = 103;

    /**
     * 获取包名
     * @param context
     * @return
     */
    public static String getPackageName(Context context){
        return context.getPackageName();
    }

    /**
     * 返回当前程序版本名
     */
    public static String getVersionName(Context context){
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(context),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }

    /**
     * 返回当前手机android版本号
     * @return
     */
    public static String getAndroidVersion(){
        String version = "";
        switch (Build.VERSION.SDK_INT){
            case Build.VERSION_CODES.BASE:
                version = "Android ";
                break;
            case Build.VERSION_CODES.BASE_1_1:
                break;
            case Build.VERSION_CODES.CUPCAKE:
                break;
            case Build.VERSION_CODES.CUR_DEVELOPMENT:
                break;
            case Build.VERSION_CODES.DONUT:
                break;
            case Build.VERSION_CODES.ECLAIR:
                break;
            case Build.VERSION_CODES.ECLAIR_0_1:
                break;
            case Build.VERSION_CODES.ECLAIR_MR1:
                break;
            case Build.VERSION_CODES.FROYO:
                break;
            case Build.VERSION_CODES.GINGERBREAD:
                break;
            case Build.VERSION_CODES.GINGERBREAD_MR1:
                break;
            case Build.VERSION_CODES.HONEYCOMB:
                break;
            case Build.VERSION_CODES.HONEYCOMB_MR1:
                break;
            case Build.VERSION_CODES.HONEYCOMB_MR2:
                break;
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH:
                break;
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1:
                break;
            case Build.VERSION_CODES.JELLY_BEAN:
                break;
            case Build.VERSION_CODES.JELLY_BEAN_MR1:
                break;
            case Build.VERSION_CODES.JELLY_BEAN_MR2:
                break;
            case Build.VERSION_CODES.KITKAT:
                break;
            case Build.VERSION_CODES.KITKAT_WATCH:
                break;
            case Build.VERSION_CODES.LOLLIPOP:
                break;
            case Build.VERSION_CODES.LOLLIPOP_MR1:
                break;
            case Build.VERSION_CODES.M:
                break;
            case Build.VERSION_CODES.N:
                break;
            case Build.VERSION_CODES.N_MR1:
                break;
            case Build.VERSION_CODES.O:
                break;
        }
        return version;
    }

    /**
     * 打开系统相机
     */
    public static File startCarmera(Activity activity){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/test/" + System.currentTimeMillis() + ".jpg");
        file.getParentFile().mkdirs();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    FileProvider.getUriForFile(activity,"com.zlcm.zlgg.fileprovider", file));
            //添加权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        activity.startActivityForResult(intent, CODE_RESULT_REQUEST);
        return file;
    }

    /**
     * 从相册选择
     */
    public static void selectFromAlbum(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, CODE_GALLERY_REQUEST);
    }

}
