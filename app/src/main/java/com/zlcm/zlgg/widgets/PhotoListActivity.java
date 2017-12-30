package com.zlcm.zlgg.widgets;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.BaseActivity;
import com.zlcm.zlgg.utils.FileUtil;
import com.zlcm.zlgg.utils.PackageUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lizhe on 2017/12/23.
 * 类介绍：
 */

public class PhotoListActivity extends BaseActivity {
    @BindView(R.id.grid_view)
    GridView mGridView;
    @BindView(R.id.title)
    TextView title;

    private ImageGridAdapter mImageGridAdapter;
    private static final int PHOTOLIST_REQ = 1;
    private File file;
    private int clipType = 0;

    @Override
    protected int setLayout() {
        return R.layout.activity_photo_list;
    }

    @Override
    protected void init() {
        title.setText("相册");
        clipType = getIntent().getIntExtra("clipType",0);
    }

    private void initListener() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    showCameraAction();
                } else {
                    startClipPicture(mImageGridAdapter.getItem(position).getPath());
                }
            }
        });
    }


    private void startClipPicture(String path) {
        Intent intent = new Intent(this, ClipPictureActivity.class);
        intent.putExtra("path", path);
        intent.putExtra("clipType",clipType);
        startActivityForResult(intent,PHOTOLIST_REQ);
    }

    /**
     * 选择相机
     */
    private void showCameraAction() {
        applyWritePermission();
    }

    /**
     * 加载手机中的相片
     */
    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media._ID};

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            CursorLoader cursorLoader = new CursorLoader(getApplicationContext(),
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                    null, null, IMAGE_PROJECTION[2] + " DESC");
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data != null) {
                List<Image> images = new ArrayList<>();
                int count = data.getCount();
                if (count > 0) {
                    data.moveToFirst();
                    do {
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                        Image image = new Image(path, name, dateTime);
                        images.add(image);
                    } while (data.moveToNext());
                    mImageGridAdapter.setData(images);
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    @Override
    protected void setData() {
        getSupportLoaderManager().initLoader(0, null, mLoaderCallback);
        initListener();
        mImageGridAdapter = new ImageGridAdapter(this);
        mGridView.setAdapter(mImageGridAdapter);
    }

    public void applyWritePermission() {

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= 23) {
            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (check == PackageManager.PERMISSION_GRANTED) {
                //调用相机
                file = PackageUtil.startCarmera(this);
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else {
            file = PackageUtil.startCarmera(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            PackageUtil.startCarmera(this);
        } else {
            // 没有获取 到权限，从新请求，或者关闭app
            Toast.makeText(this, "需要存储权限", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.img_lift)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTOLIST_REQ){
            String url = null;
            if (data != null) {
                url = data.getStringExtra("bitmap");
            }
            if (url != null){
                Intent intent = new Intent();
                intent.putExtra("bitmap",url);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        }else if (requestCode == PackageUtil.CODE_RESULT_REQUEST){
            if (resultCode == Activity.RESULT_OK) {
                startClipPicture(file.getAbsolutePath());
            }
        }
    }
}
