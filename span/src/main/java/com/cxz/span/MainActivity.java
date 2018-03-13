package com.cxz.span;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spanned;
import android.util.FloatProperty;
import android.util.Property;
import android.widget.TextView;
import android.widget.Toast;

import com.cxz.span.spans.FireworksSpanGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_1)
    TextView tv1;
    private Unbinder bind;
    private static FireworksSpanGroup spanGroup = new FireworksSpanGroup(255);
    private Property<FireworksSpanGroup, Float> FIREWORKS_SPAN_GROUP_FLOAT_PROPERTY =
            new FloatProperty<FireworksSpanGroup>("FIREWORKS_GROUP_PROGRESS_PROPERTY") {
                @Override
                public void setValue(FireworksSpanGroup object, float value) {

                }

                @Override
                public Float get(FireworksSpanGroup object) {
                    return null;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        init();
    }

    private void init() {
//        final FireworksSpanGroup spanGroup = new FireworksSpanGroup();
        String text = tv1.getText().toString();
//        //初始化包含多个spans的grop
//        spanGroup.addSpan(span);
//        //给ActionBar的标题设置spans
//        text.setSpan(span, 0, text.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spanGroup.init();
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(spanGroup, FIREWORKS_GROUP_PROGRESS_PROPERTY, 0.0f, 1.0f);
//        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                //更新标题
//                setTitle(mActionBarTitleSpannableString);
//            }
//        });
//        objectAnimator.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

}
