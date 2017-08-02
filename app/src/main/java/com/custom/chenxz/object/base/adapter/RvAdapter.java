package com.custom.chenxz.object.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.custom.chenxz.object.R;
import com.custom.chenxz.object.databean.TextListData;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvViewHolder> {
    private List<TextListData> datas;
    private Context context;
    private int time;

    public RvAdapter(Context context, List<TextListData> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("CXZ", "类名:RvAdapter  方法名: onCreateViewHolder " + time++);
        return new RvViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv, null));
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        Log.e("CXZ", "类名:RvAdapter  方法名: onBindViewHolder " + position);
        holder.tv.setText(datas.get(position).getMsg());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public static class RvViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv;

        public RvViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }
}
