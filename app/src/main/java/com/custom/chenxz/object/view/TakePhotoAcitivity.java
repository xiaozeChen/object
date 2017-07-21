package com.custom.chenxz.object.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.custom.chenxz.object.R;
import com.custom.chenxz.object.utils.FileProvider7;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/21.
 */

public class TakePhotoAcitivity extends AppCompatActivity {
    private static final int REQUEST_CODE_TAKE_PHOTO = 0x110;
    private static final int REQUEST_CAMERA_PERMISSION = 0x200;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.btn_takePhoto)
    Button btnTakePhoto;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
    }

    /**
     * 动态请求权限
     *
     * @param context
     * @param permission
     */
    public static void requestPermission(Activity context, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断当前的系统版本是否大于M 23
            ActivityCompat.requestPermissions(context, new String[]{permission}, REQUEST_CAMERA_PERMISSION);
            return;
        }
    }

    public void takePhotoNoCompress() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA).format(new Date()) + ".png";
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            mCurrentPhotoPath = file.getAbsolutePath();
            Uri fileUri = FileProvider7.getUriForFile(this, file);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_TAKE_PHOTO:
                    ivPhoto.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
                    break;

            }
        }

    }

    /**
     * 返回权限申请结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION: {//申请相机权限
                //判断用户的选择 是同意还是拒绝
                int grantResult = grantResults[0];//获取权限的申请结果
                if (requestCode == REQUEST_CAMERA_PERMISSION && grantResult == PackageManager.PERMISSION_GRANTED) {//如果用户同意了  PackageManager.PERMISSION_DENIED代表拒绝
                    takePhotoNoCompress();
                } else {
                    Toast.makeText(this, "获取相机权限失败！", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @OnClick(R.id.btn_takePhoto)
    public void onViewClicked(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermission(this, Manifest.permission.CAMERA);
            return;
        } else {
            takePhotoNoCompress();
        }
    }
}
