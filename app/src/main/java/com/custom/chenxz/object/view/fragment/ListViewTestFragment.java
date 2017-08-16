package com.custom.chenxz.object.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.custom.chenxz.object.R;
import com.custom.chenxz.object.base.adapter.CommonListViewAdapter;
import com.custom.chenxz.object.base.adapter.ViewHolder;
import com.custom.chenxz.object.widget.FlowLayout;
import com.custom.chenxz.object.widget.TagAdapter;
import com.custom.chenxz.object.widget.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhy on 15/9/10.
 */
public class ListViewTestFragment extends Fragment {

    private List<List<String>> mDatas = new ArrayList<List<String>>();
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listview, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        initDatas();

        mListView = (ListView) view.findViewById(R.id.id_listview);
        CommonListViewAdapter<List<String>> commonAdapter = new CommonListViewAdapter<List<String>>(getContext(), R.layout.item_for_listview, mDatas) {

            @Override
            public void convert(final ViewHolder holder, List<String> strings) {
                TagFlowLayout tagFlowLayout = holder.getView(R.id.id_tagflowlayout);

                TagAdapter<String> tagAdapter = new TagAdapter<String>(strings) {
                    @Override
                    public View getView(FlowLayout parent, int position, String o) {
                        //can use viewholder
                        TextView tv = (TextView) mInflater.inflate(R.layout.tv, parent, false);
                        tv.setText(o);
                        return tv;
                    }
                };
                tagFlowLayout.setAdapter(tagAdapter);
                //重置状态
                tagAdapter.setSelectedList(selectedMap.get(holder.getPosition()));

                tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                    @Override
                    public void onSelected(Set<Integer> selectPosSet) {
                        selectedMap.put(holder.getPosition(), selectPosSet);
                    }
                });
            }

            Map<Integer, Set<Integer>> selectedMap = new HashMap<Integer, Set<Integer>>();

        };
        mListView.setAdapter(commonAdapter);

    }

    private void initDatas() {
        for (int i = 'A'; i < 'z'; i++) {
            List<String> itemData = new ArrayList<String>(3);
            for (int j = 0; j < 3; j++) {
                itemData.add((char) i + "");
            }
            mDatas.add(itemData);
        }
    }
}
