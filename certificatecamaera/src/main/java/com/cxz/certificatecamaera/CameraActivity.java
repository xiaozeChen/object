package com.cxz.certificatecamaera;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 证件拍摄页面
 */

public class CameraActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    /**
     * 拍摄类型-身份证正面
     */
    public final static int TYPE_IDCARD_FRONT = 1;
    /**
     * 拍摄类型-身份证反面
     */
    public final static int TYPE_IDCARD_BACK = 2;
    /**
     * 拍摄类型-竖版营业执照
     */
    public final static int TYPE_COMPANY_PORTRAIT = 3;
    /**
     * 拍摄类型-横版营业执照
     */
    public final static int TYPE_COMPANY_LANDSCAPE = 4;
    //页面跳转请求
    public final static int REQUEST_CODE = 0X13;
    public final static int RESULT_CODE = 0X14;

    private int type;
    private String fileName;
    private CameraPreview cameraPreview;
    private CheckBox btnFlash;
    private LinearLayout containerView, layoutOption, layoutResult;
    private ImageView cropView;

    /**
     * 开启相机
     *
     * @param type {@link #TYPE_IDCARD_FRONT}
     *             {@link #TYPE_IDCARD_BACK}
     *             {@link #TYPE_COMPANY_PORTRAIT}
     *             {@link #TYPE_COMPANY_LANDSCAPE}
     */
    public static void openCertificateCamera(Activity activity, int type, String fileName) {
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("fileName", fileName);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", 0);
        fileName = getIntent().getStringExtra("fileName");
        //设置屏幕方向
        setRequestedOrientation(type == TYPE_COMPANY_PORTRAIT ?
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_camera);
        //设置全屏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        findView();
        initData();
    }

    /**
     * 获取控件
     */
    private void findView() {
        cameraPreview = findViewById(R.id.camera_surface);
        cropView = findViewById(R.id.camera_crop);
        btnFlash = findViewById(R.id.camera_flash);
        layoutOption = findViewById(R.id.camera_option);
        layoutResult = findViewById(R.id.camera_result);
        containerView = findViewById(R.id.camera_crop_container);
        cameraPreview.setOnClickListener(this);
        btnFlash.setOnCheckedChangeListener(this);
        findViewById(R.id.camera_close).setOnClickListener(this);
        findViewById(R.id.camera_take).setOnClickListener(this);
        findViewById(R.id.camera_result_ok).setOnClickListener(this);
        findViewById(R.id.camera_result_cancel).setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //获取屏幕最小边，设置为cameraPreview较窄的一边
        float screenMinSize = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        //根据screenMinSize，计算出cameraPreview的较宽的一边，长宽比为标准的16:9
        float maxSize = screenMinSize / 9.0f * 16.0f;
        //设置相机预览布局
        RelativeLayout.LayoutParams layoutParams = type == TYPE_COMPANY_PORTRAIT ?
                new RelativeLayout.LayoutParams((int) screenMinSize, (int) maxSize) :
                new RelativeLayout.LayoutParams((int) maxSize, (int) screenMinSize);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        cameraPreview.setLayoutParams(layoutParams);
        //设置剪裁边框和剪裁提示布局
        float width, height;
        LinearLayout.LayoutParams containerParams;
        LinearLayout.LayoutParams cropParams;
        if (type == TYPE_COMPANY_PORTRAIT) {
            width = (int) (screenMinSize * 0.8);
            height = (int) (width * 43.0f / 30.0f);
            containerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height);
            cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
        } else if (type == TYPE_COMPANY_LANDSCAPE) {
            height = (int) (screenMinSize * 0.8);
            width = (int) (height * 43.0f / 30.0f);
            containerParams = new LinearLayout.LayoutParams((int) width, ViewGroup.LayoutParams.MATCH_PARENT);
            cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
        } else {
            height = (int) (screenMinSize * 0.75);
            width = (int) (height * 75.0f / 47.0f);
            containerParams = new LinearLayout.LayoutParams((int) width, ViewGroup.LayoutParams.MATCH_PARENT);
            cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
        }
        containerView.setLayoutParams(containerParams);
        cropView.setLayoutParams(cropParams);
        //设置提示图片
        switch (type) {
            case TYPE_IDCARD_FRONT:
                cropView.setImageResource(R.drawable.camera_idcard_front);
                break;
            case TYPE_IDCARD_BACK:
                cropView.setImageResource(R.drawable.camera_idcard_back);
                break;
            case TYPE_COMPANY_PORTRAIT:
                cropView.setImageResource(R.drawable.camera_company);
                break;
            case TYPE_COMPANY_LANDSCAPE:
                cropView.setImageResource(R.drawable.camera_company_landscape);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.camera_surface) {
            cameraPreview.focus();
        } else if (id == R.id.camera_close) {
            finish();
        } else if (id == R.id.camera_take) {
            takePhoto();
        } else if (id == R.id.camera_result_ok) {
            goBack();
        } else if (id == R.id.camera_result_cancel) {
            layoutOption.setVisibility(View.VISIBLE);
            cameraPreview.setEnabled(true);
            layoutResult.setVisibility(View.GONE);
            cameraPreview.startPreview();
        }
    }

    /**
     * 闪光灯开关
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        cameraPreview.switchFlashLight(isChecked);
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        layoutOption.setVisibility(View.GONE);
        cameraPreview.setEnabled(false);
        cameraPreview.takePhoto(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(final byte[] data, Camera camera) {
                //子线程处理图片，防止ANR
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //先获取原始图片
                            File originalFile = getOriginalFile();
                            FileOutputStream originalFileOutputStream = new FileOutputStream(originalFile);
                            originalFileOutputStream.write(data);
                            originalFileOutputStream.close();
                            Bitmap bitmap = BitmapFactory.decodeFile(originalFile.getPath());

                            //计算裁剪位置
                            float left, top, right, bottom;
                            if (type == TYPE_COMPANY_PORTRAIT) {
                                left = (float) cropView.getLeft() / (float) cameraPreview.getWidth();
                                top = ((float) containerView.getTop() - (float) cameraPreview.getTop()) / (float) cameraPreview.getHeight();
                                right = (float) cropView.getRight() / (float) cameraPreview.getWidth();
                                bottom = (float) containerView.getBottom() / (float) cameraPreview.getHeight();
                            } else {
                                left = ((float) containerView.getLeft() - (float) cameraPreview.getLeft()) / (float) cameraPreview.getWidth();
                                top = (float) cropView.getTop() / (float) cameraPreview.getHeight();
                                right = (float) containerView.getRight() / (float) cameraPreview.getWidth();
                                bottom = (float) cropView.getBottom() / (float) cameraPreview.getHeight();
                            }
                            //裁剪及保存到文件
                            Bitmap cropBitmap = Bitmap.createBitmap(bitmap,
                                    (int) (left * (float) bitmap.getWidth()),
                                    (int) (top * (float) bitmap.getHeight()),
                                    (int) ((right - left) * (float) bitmap.getWidth()),
                                    (int) ((bottom - top) * (float) bitmap.getHeight()));
                            File cropFile = getCropFile();
                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(cropFile));
                            cropBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                            bos.flush();
                            bos.close();
                            //删除原始图片
                            originalFile.delete();
                            //回收资源
                            bitmap.recycle();
                            cropBitmap.recycle();
                            //显示结果
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    layoutResult.setVisibility(View.VISIBLE);
                                }
                            });
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //显示拍照后操作布局
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                layoutOption.setVisibility(View.VISIBLE);
                                cameraPreview.setEnabled(true);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    /**
     * 点击对勾，使用拍照结果，返回对应图片路径
     */
    private void goBack() {
        Intent intent = new Intent();
        intent.putExtra("result", getCropFile().getPath());
        setResult(RESULT_CODE, intent);
        finish();
    }

    /**
     * @return 拍摄图片原始文件
     */
    private File getOriginalFile() {
        File dirFile = new File(Environment.getExternalStorageDirectory() + "/simple");
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return new File(dirFile, fileName + "_original.jpg");
    }

    /**
     * @return 拍摄图片裁剪文件
     */
    private File getCropFile() {
        File dirFile = new File(Environment.getExternalStorageDirectory() + "/simple");
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return new File(dirFile, fileName + "_crop.jpg");
    }

}
