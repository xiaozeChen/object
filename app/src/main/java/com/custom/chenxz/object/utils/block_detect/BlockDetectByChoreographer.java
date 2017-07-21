package com.custom.chenxz.object.utils.block_detect;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Choreographer;

public class BlockDetectByChoreographer {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void start() {
        Choreographer.getInstance()
                .postFrameCallback(new Choreographer.FrameCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void doFrame(long l) {
                        if (LogMonitor.getInstance().isMonitor()) {
                            LogMonitor.getInstance().removeMonitor();
                        }
                        LogMonitor.getInstance().startMonitor();
                        Choreographer.getInstance().postFrameCallback(this);
                    }
                });
    }
}