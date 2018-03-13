package com.cxz.span.spans;

import android.graphics.Color;
import android.os.Parcel;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;

/**
 * 文字前景色
 */

public class MutableForegroundColorSpan extends ForegroundColorSpan {
    private int mAlpha = 255;
    private int mForegroundColor;

    public MutableForegroundColorSpan(int color) {
        super(color);
        mAlpha = Color.alpha(color);
        mForegroundColor = color;
    }

    public MutableForegroundColorSpan(Parcel src) {
        super(src);
        mForegroundColor = src.readInt();
        mAlpha = src.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mForegroundColor);
        dest.writeFloat(mAlpha);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(getForegroundColor());
    }

    /**
     * @param alpha alpha from 0 to 255
     */
    public void setAlpha(int alpha) {
        this.mAlpha = alpha;
    }

    public void setForegroundColor(int foregroundColor) {
        this.mForegroundColor = foregroundColor;
    }

    public int getAlpha() {
        return mAlpha;
    }

    public int getForegroundColor() {
        return Color.argb(mAlpha, Color.red(mForegroundColor), Color.green(mForegroundColor), Color.blue(mForegroundColor));
    }

}
