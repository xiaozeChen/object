package com.custom.chenxz.object.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.custom.chenxz.object.R;
import com.custom.chenxz.object.wedget.SaleProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaleProgressViewActivity extends Activity {

    @BindView(R.id.spv)
    SaleProgressView saleProgressView;
    @BindView(R.id.seek)
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_progress_view);
        ButterKnife.bind(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                saleProgressView.setTotalAndCurrentCount(100,i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
