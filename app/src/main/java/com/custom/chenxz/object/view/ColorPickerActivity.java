package com.custom.chenxz.object.view;

import android.app.Activity;
import android.os.Bundle;

import com.custom.chenxz.object.R;
import com.custom.chenxz.object.utils.apputils.LogUtils;
import com.custom.chenxz.object.wedget.ColorPickerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorPickerActivity extends Activity {

    @BindView(R.id.view_ColorPicker)
    ColorPickerView viewColorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        ButterKnife.bind(this);
        viewColorPicker.setOnColorChangedListenner(new ColorPickerView.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color, int originalColor, float saturation) {
                LogUtils.i("color:" + color + "originalColor:" + originalColor + "saturation:" + saturation);
            }
        });
    }

}
