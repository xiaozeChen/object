package com.custom.chenxz.object.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;


/**
 * 单一列表适配器
 * 使用方法
 * scheduleAdapter = new CommonAdapter<ScheduleData>(this, R.layout.item_schedule, scheduleDatas) {
 *
 * @Override protected void convert(ViewHolder holder, ScheduleData scheduleData, int position) {
 * holder.setText(R.id.tv_scheduleTitle, scheduleData.getName());
 * holder.setImageUrl(R.id.iv_schedulePic, scheduleData.getImgUrl());
 * holder.setTextColor(R.id.tv_scheduleSynopsis, Global.textLevel1);
 * }
 * };
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public CommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);


}
