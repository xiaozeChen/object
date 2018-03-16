package com.cxz.certificatecamaera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;

/**
 * 相机工具类
 */
public class CameraUtils {
    private static final String TAG = CameraUtils.class.getName();
    /**
     * 检测当前设备是否支持相机拍照
     */
    public static boolean hasCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 安全打开一个相机实例的方法，防止多次调用相机
     */
    public static Camera openCamera() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return c; // returns null if camera is unavailable
    }
}
