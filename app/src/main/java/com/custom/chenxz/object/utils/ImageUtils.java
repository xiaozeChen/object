package com.custom.chenxz.object.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/11/3.
 */

public class ImageUtils {

    /**
     * 质量压缩
     *
     * @param resourceId
     * @param quality
     */
    public void qualityCompress(Context context, int resourceId, int quality) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        //保存图片到本地
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "test.jpg");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            //quality 为0～100，0表示最小体积，100表示最高质量，对应体积也是最大
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            Log.e("CXZ", "类名:ImageUtils  方法名: qualityCompress file.length:" + file.length());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //查看压缩之后的bitmap大小
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        Bitmap compress = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Log.e("CXZ", "onCreate: bitmap.size = " + bitmap.getByteCount() + "   compress.size = " + compress.getByteCount());
    }


}
