package com.custom.chenxz.object;

import android.app.Application;

import com.custom.chenxz.photolibrary.Awen;

/**
 * Created by Administrator on 2017/7/21.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Awen.init(this);
        //测试ui绘制过程耗时
//        BlockDetectByPrinter.start();
    }
}
