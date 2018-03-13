package com.cxz.span.spans;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 烟火效果字体样式
 */

public class FireworksSpanGroup {
    private float mAlpha;
    private ArrayList<MutableForegroundColorSpan> colorSpans;

    public FireworksSpanGroup(float mAlpha) {
        this.mAlpha = mAlpha;
        colorSpans = new ArrayList<>();
    }

    public void addSpan(MutableForegroundColorSpan span) {
        span.setAlpha((int) (mAlpha * 255));
        colorSpans.add(span);
    }

    public void init() {
        Collections.shuffle(colorSpans);
    }

    public void setAlpha(float alpha) {
        int size = colorSpans.size();
        float total = 1.0f * size * alpha;
        for (MutableForegroundColorSpan colorSpan : colorSpans) {
            if (total >= 1.0f) {
                colorSpan.setAlpha(255);
                total -= 1.0f;
            } else {
                colorSpan.setAlpha((int) (total * 255));
                total = 0.0f;
            }
        }
    }

    public float getAlpha() {
        return mAlpha;
    }
}
