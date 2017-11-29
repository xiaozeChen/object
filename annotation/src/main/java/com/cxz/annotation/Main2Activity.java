package com.cxz.annotation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.cxz.annotation.annotations.BindView;

public class Main2Activity extends AppCompatActivity {
    @BindView(R.id.textView)
    private TextView textView;
    @BindView(R.id.button)
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}
