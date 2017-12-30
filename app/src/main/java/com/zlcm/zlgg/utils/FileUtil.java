package com.zlcm.zlgg.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.zlcm.zlgg.app.App;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/17 0017.
 */

public class FileUtil {


    public static void writeLogCrashContent(final String content){
        new Thread(new Runnable() {
            @Override
            public void run() {
                writeFile(getFileDir("Log")+"/crash.log", TimeUtils.getCurTimeString() + " : " + content, true);
            }
        }).start();
    }

    /**
     * 往文件写错误信息
     * @param filePath
     * @param context
     * @param append
     * @return
     */
    public static boolean writeFile(String filePath, String context, boolean append){
        if (TextUtils.isEmpty(context)){
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath,append);
            fileWriter.write(context);
            fileWriter.write("\r\n");
            fileWriter.write("\r\n");
            fileWriter.close();
            return true;
        } catch (IOException e) {
            Log.e("IOException occurred. ", e.getMessage(),e);
            return false;
        }finally {
            if (fileWriter != null){
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    Log.e("IOException occurred. ", e.getMessage(), e);
                }
            }
        }

    }

    private static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)){
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    private static String getFolderName(String filePath) {
        if (TextUtils.isEmpty(filePath)){
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0,filePosi);
    }

    /**
     * 获取文件路径
     * @param filePath
     * @return
     */
    public static String getFileDir(String filePath) {
        String dir;
        if (isExistSDCard()) {
            dir = App.getInstance().getContext().getExternalFilesDir("").getAbsolutePath();
        } else {
            dir = App.getInstance().getContext().getFilesDir().getAbsolutePath();
        }

        if (TextUtils.isEmpty(filePath))
            return dir;
        else {
            if (filePath.startsWith(File.separator)) {
                dir += filePath;
            } else
                dir += File.separator + filePath;


            FileUtil.makeDirs(dir);

            return dir;
        }
    }

    /**
     * 是否插入sd卡
     * @return
     */
    public static boolean isExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        }
        else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 检查文件是否存在
     */
    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }
}
