package com.custom.chenxz.object;

import android.app.Application;

import com.custom.chenxz.object.utils.block_detect.BlockDetectByPrinter;

/**
 * Created by Administrator on 2017/7/21.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //测试ui绘制过程耗时
        BlockDetectByPrinter.start();
    }
}
