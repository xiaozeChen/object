package com.custom.chenxz.object.base.adapter;


/**
 * 多布局列表泛型定义
 */
public interface ItemViewDelegate<T> {

    /**
     * 获取当前视图布局id
     *
     * @return
     */
    int getItemViewLayoutId();

    /**
     * 判断是否是当前的视图类型
     *
     * @param item
     * @param position
     * @return
     */
    boolean isForViewType(T item, int position);

    /**
     * 绑定视图数据
     *
     * @param holder
     * @param t
     * @param position
     */
    void convert(ViewHolder holder, T t, int position);

}
