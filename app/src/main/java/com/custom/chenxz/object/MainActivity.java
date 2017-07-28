package com.custom.chenxz.object;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.custom.chenxz.object.server.VideoLiveWallpaper;
import com.custom.chenxz.object.view.NestedScrollActivity;
import com.custom.chenxz.object.view.TakePhotoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_android7)
    Button btnAndroid7;
    @BindView(R.id.btn_LiveWallPaper)
    Button btnLiveWallPaper;
    @BindView(R.id.cb_voice)
    CheckBox cbVoice;
    @BindView(R.id.btn_NestedScroll)
    Button btnNestedScroll;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        cbVoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 静音
                    VideoLiveWallpaper.voiceSilence(getApplicationContext());
                } else {
                    VideoLiveWallpaper.voiceNormal(getApplicationContext());
                }
            }
        });
    }

    @OnClick({R.id.btn_android7, R.id.btn_LiveWallPaper, R.id.btn_NestedScroll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_android7:
                startActivity(new Intent(this, TakePhotoActivity.class));
                break;
            case R.id.btn_LiveWallPaper:
                VideoLiveWallpaper.setToWallPaper(this);
                break;
            case R.id.btn_NestedScroll:
                startActivity(new Intent(this, NestedScrollActivity.class));
                break;
        }
    }

    public static void setToWallPaper(Context context) {
        final Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(context, VideoLiveWallpaper.class));
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
            bind = null;
        }
    }

    @OnClick(R.id.btn_NestedScroll)
    public void onViewClicked() {
    }
}
