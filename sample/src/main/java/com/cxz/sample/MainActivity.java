package com.cxz.sample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cxz.certificatecamaera.CameraActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.main_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CameraActivity.REQUEST_CODE && resultCode == CameraActivity.RESULT_CODE) {
            //获取文件路径，显示图片
            String path =  data.getStringExtra("result");
            Log.e("CXZ", "类名:MainActivity  方法名: onActivityResult " + path);
            if (!TextUtils.isEmpty(path)) {
                Glide.with(this).load(path).into(imageView);
            }
        }
    }

    /**
     * 拍摄证件照片
     *
     * @param type 拍摄证件类型
     */
    private void takePhoto(int type) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0x12);
            return;
        }
        CameraActivity.openCertificateCamera(this, type, "test");
    }

    /**
     * 身份证正面
     */
    public void frontIdCard(View view) {
        takePhoto(CameraActivity.TYPE_IDCARD_FRONT);
    }

    /**
     * 身份证反面
     */
    public void backIdCard(View view) {
        takePhoto(CameraActivity.TYPE_IDCARD_BACK);
    }

    /**
     * 营业执照竖版
     */
    public void businessLicensePortrait(View view) {
        takePhoto(CameraActivity.TYPE_COMPANY_PORTRAIT);
    }

    /**
     * 营业执照横版
     */
    public void businessLicenseLandscape(View view) {
        takePhoto(CameraActivity.TYPE_COMPANY_LANDSCAPE);
    }

}
