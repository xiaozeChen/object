package com.custom.chenxz.object.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.custom.chenxz.object.R;
import com.custom.chenxz.object.wedget.WireWalkingDownloadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WireWalkingDownloadingActivity extends Activity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.show_success)
    Button showSuccess;
    @BindView(R.id.show_failed)
    Button showFailed;
    private static final int UPDATE_PROGRESS_DELAY = 500;
    private int mProgress;
    private Handler mHandler = new Handler();
    private boolean isSuccess = true;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mProgress += 10;
            if (!isSuccess && mProgress >= 60) {
                wwDownloading.onFail();
            }
            wwDownloading.updateProgress(mProgress);
            mHandler.postDelayed(mRunnable, UPDATE_PROGRESS_DELAY);
        }
    };

    @BindView(R.id.ww_downloading)
    WireWalkingDownloadingView wwDownloading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wire_walking_downloading);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);

    }

    @OnClick({R.id.show_success, R.id.show_failed})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.show_success:
                wwDownloading.releaseAnimation();
                mHandler.removeCallbacks(mRunnable);
                isSuccess = true;
                mProgress = 0;
                wwDownloading.performAnimation();
                wwDownloading.updateProgress(0);
                mHandler.postDelayed(mRunnable, 0);
                break;
            case R.id.show_failed:
                wwDownloading.releaseAnimation();
                mHandler.removeCallbacks(mRunnable);
                isSuccess = false;
                mProgress = 0;
                wwDownloading.performAnimation();
                wwDownloading.updateProgress(0);
                mHandler.postDelayed(mRunnable, 0);
                break;
        }
    }
}
