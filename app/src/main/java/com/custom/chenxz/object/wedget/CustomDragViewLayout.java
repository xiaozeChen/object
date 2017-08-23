package com.custom.chenxz.object.wedget;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * API分析：
 * public static ViewDragHelper create(ViewGroup forParent, float sensitivity, Callback cb)
 * sensitivity越大,对滑动的检测就越敏感,默认传1即可
 * <p>
 * public void setEdgeTrackingEnabled(int edgeFlags)
 * 设置允许父View的某个边缘可以用来响应托拽事件，
 * <p>
 * public boolean shouldInterceptTouchEvent(MotionEvent ev)
 * 在父view onInterceptTouchEvent方法中调用
 * <p>
 * public void processTouchEvent(MotionEvent ev)
 * 在父view onTouchEvent方法中调用
 * <p>
 * public void captureChildView(View childView, int activePointerId)
 * 在父View内捕获指定的子view用于拖曳，会回调tryCaptureView()
 * <p>
 * public boolean smoothSlideViewTo(View child, int finalLeft, int finalTop)
 * 某个View自动滚动到指定的位置，初速度为0，可在任何地方调用，动画移动会回调continueSettling(boolean)方法，直到结束
 * <p>
 * public boolean settleCapturedViewAt(int finalLeft, int finalTop)
 * 以松手前的滑动速度为初值，让捕获到的子View自动滚动到指定位置，只能在Callback的onViewReleased()中使用，其余同上
 * <p>
 * public void flingCapturedView(int minLeft, int minTop, int maxLeft, int maxTop)
 * 以松手前的滑动速度为初值，让捕获到的子View在指定范围内fling惯性运动，只能在Callback的onViewReleased()中使用，其余同上
 * <p>
 * public boolean continueSettling(boolean deferCallbacks)
 * 在调用settleCapturedViewAt()、flingCapturedView()和smoothSlideViewTo()时，该方法返回true，一般重写父view的computeScroll方法，进行该方法判断
 * <p>
 * public void abort()
 * 中断动画
 */
public class CustomDragViewLayout extends ViewGroup {
    public ViewDragHelper mViewDragHelper;
    private boolean isOpen = true;
    private View mMenuView;
    private View mContentView;
    private int mCurrentTop = 0;

    public CustomDragViewLayout(Context context) {
        this(context, null);
    }

    public CustomDragViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomDragViewLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //ViewDragHelper静态方法传入ViewDragHelperCallBack创建
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelperCallBack());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM);
    }

    /**
     * public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy)
     * 被拖拽的View位置变化时回调，changedView为位置变化的view，left、top变化后的x、y坐标，dx、dy为新位置与旧位置的偏移量
     * <p>
     * public void onViewDragStateChanged(int state)
     * 当ViewDragHelper状态发生变化时回调（STATE_IDLE,STATE_DRAGGING,STATE_SETTLING）
     * <p>
     * public void onViewCaptured(View capturedChild, int activePointerId)
     * 成功捕获到子View时或者手动调用captureChildView()时回调
     * <p>
     * public void onViewReleased(View releasedChild, float xvel, float yvel)
     * 当前拖拽的view松手或者ACTION_CANCEL时调用，xvel、yvel为离开屏幕时的速率
     * <p>
     * public void onEdgeTouched(int edgeFlags, int pointerId)
     * 当触摸到边界时回调
     * <p>
     * public boolean onEdgeLock(int edgeFlags)
     * true的时候会锁住当前的边界，false则unLock。锁定后的边缘就不会回调onEdgeDragStarted()
     * <p>
     * public void onEdgeDragStarted(int edgeFlags, int pointerId)
     * ACTION_MOVE且没有锁定边缘时触发，在此可手动调用captureChildView()触发从边缘拖动子View
     * <p>
     * public int getOrderedChildIndex(int index)
     * 寻找当前触摸点View时回调此方法，如需改变遍历子view顺序可重写此方法
     * <p>
     * public int getViewHorizontalDragRange(View child)
     * 返回拖拽子View在相应方向上可以被拖动的最远距离，默认为0
     * <p>
     * public int getViewVerticalDragRange(View child)
     * 返回拖拽子View在相应方向上可以被拖动的最远距离，默认为0
     * <p>
     * public abstract boolean tryCaptureView(View child, int pointerId)
     * 对触摸view判断，如果需要当前触摸的子View进行拖拽移动就返回true，否则返回false
     * <p>
     * public int clampViewPositionHorizontal(View child, int left, int dx)
     * 拖拽的子View在所属方向上移动的位置，child为拖拽的子View，left为子view应该到达的x坐标，dx为挪动差值
     * <p>
     * public int clampViewPositionVertical(View child, int top, int dy)
     * 同上，top为子view应该到达的y坐标
     */
    private class ViewDragHelperCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //返回ture则表示可以捕获该view
            return child == mContentView;
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            //setEdgeTrackingEnabled设置的边界滑动时触发
            //通过captureChildView对其进行捕获，该方法可以绕过tryCaptureView
            mViewDragHelper.captureChildView(mContentView, pointerId);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //手指触摸移动时回调, left表示要到的x坐标
            return super.clampViewPositionHorizontal(child, left, dx);//
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //手指触摸移动时回调 top表示要到达的y坐标
            return Math.max(Math.min(top, mMenuView.getHeight()), 0);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            //手指抬起释放时回调
            int finalTop = mMenuView.getHeight();
            if (yvel <= 0) {
                if (releasedChild.getTop() < mMenuView.getHeight() / 2) {
                    finalTop = 0;
                } else {
                    finalTop = mMenuView.getHeight();
                }
            } else {
                if (releasedChild.getTop() > mMenuView.getHeight() / 2) {
                    finalTop = mMenuView.getHeight();
                } else {
                    finalTop = 0;
                }
            }
            //以松手前的滑动速度为初值，让捕获到的子View自动滚动到指定位置，只能在Callback的onViewReleased()中使用
            mViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), finalTop);
            invalidate();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            //mDrawerView完全覆盖屏幕则防止过度绘制
            mMenuView.setVisibility((changedView.getHeight() - top == getHeight()) ? View.GONE : View.VISIBLE);
            mCurrentTop += dy;
            requestLayout();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            if (mMenuView == null) return 0;
            return (mContentView == child) ? mMenuView.getHeight() : 0;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (state == ViewDragHelper.STATE_IDLE) {
                isOpen = (mContentView.getTop() == mMenuView.getHeight());
            }
        }
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    public boolean isDrawerOpened() {
        return isOpen;
    }

    //onInterceptTouchEvent方法调用ViewDragHelper.shouldInterceptTouchEvent
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    //onTouchEvent方法中调用ViewDragHelper.processTouchEvent方法并返回true
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 当ViewGroup中所有的子View全部加载完成时回调该方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mContentView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mMenuView.layout(0, 0,
                mMenuView.getMeasuredWidth(),
                mMenuView.getMeasuredHeight());
        mContentView.layout(0, mCurrentTop + mMenuView.getHeight(),
                mContentView.getMeasuredWidth(),
                mCurrentTop + mContentView.getMeasuredHeight() + mMenuView.getHeight());

    }
}