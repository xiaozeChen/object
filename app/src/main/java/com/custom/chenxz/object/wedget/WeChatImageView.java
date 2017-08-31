package com.custom.chenxz.object.wedget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.custom.chenxz.object.R;

/**
 * 自定义微信聊天图片
 */
public class WeChatImageView extends AppCompatImageView {

    private int mWidth;
    private int mHeight;
    private float distanceTop;
    private float distanceLeft;
    private Paint mPaint;
    private Bitmap canvasBitmap;
    private Paint paint;
    private boolean direction;//默认为左边的箭头
    private RectF rectF;

    public WeChatImageView(Context context) {
        this(context, null);
    }

    public WeChatImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeChatImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WeChatImageView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.WeChatImageView_direction_left:
                    direction = a.getBoolean(attr, true);
                    break;
                case R.styleable.WeChatImageView_distance_MarginTop:
                    // 三角形距离顶部的距离
                    distanceTop = a.getDimension(attr, 50);
                    break;
                case R.styleable.WeChatImageView_distance_PaddingLeft:
                    // 三角形的宽度
                    distanceLeft = a.getDimension(attr, 25);
                    break;
            }
        }
        a.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
    }

    private void ImgShape() {
        canvasBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);
        Path path = new Path();
        double sqrt = Math.sqrt(3)/3;
        if (direction) {//箭头向左为true
            path.moveTo(distanceLeft, distanceTop);
            path.lineTo(distanceLeft, distanceTop + (float) (sqrt * distanceLeft * 2));
            path.lineTo(0, distanceTop + (float) (sqrt * distanceLeft));
            path.close();
            rectF = new RectF(distanceLeft, 0, mWidth, mHeight);
        } else {
            path.moveTo(mWidth - distanceLeft, distanceTop);
            path.lineTo(mWidth - distanceLeft, distanceTop + (float) (sqrt * distanceLeft * 2));
            path.lineTo(mWidth,  distanceTop + (float) (sqrt * distanceLeft));
            path.close();
            rectF = new RectF(0, 0, mWidth - distanceLeft, mHeight);
        }
        canvas.drawRoundRect(rectF, 20, 20, paint);
        canvas.drawPath(path, paint);
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        ImgShape();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        int count = canvas.saveLayerAlpha(0, 0, mWidth, mHeight, 250, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        super.onDraw(canvas);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(canvasBitmap, 0, 0, mPaint);
        canvas.restoreToCount(count);
    }

}