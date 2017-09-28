package com.custom.chenxz.object.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.custom.chenxz.object.R;
import com.custom.chenxz.object.base.adapter.CommonAdapter;
import com.custom.chenxz.object.base.adapter.ViewHolder;
import com.custom.cxz.coverflow.CoverFlowLayoutManger;
import com.custom.cxz.coverflow.RecyclerCoverFlow;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoverFlowActivity extends AppCompatActivity {

    @BindView(R.id.tv_image_describe)
    TextView tvImageDescribe;
    @BindView(R.id.list)
    RecyclerCoverFlow recyclerCoverFlow;
    private ArrayList<Integer> mList;
    private int[] images = new int[]{R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
//            R.drawable.pic4, R.drawable.pic5, R.drawable.pic6,
//            R.drawable.pic7, R.drawable.pic8, R.drawable.pic9,
            R.drawable.pic10, R.drawable.pic11};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_flow);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int image : images) {
            mList.add(image);
        }
//        recyclerCoverFlow.setFlatFlow(true); //平面滚动
//        recyclerCoverFlow.setGreyItem(true); //设置灰度渐变
//        recyclerCoverFlow.setAlphaItem(true); //设置半透渐变
        recyclerCoverFlow.setAdapter(new CommonAdapter<Integer>(this, R.layout.item_cover_flow, mList) {
            @Override
            protected void convert(ViewHolder holder, Integer integer, int position) {
                holder.setImageResource(R.id.iv_item, mDatas.get(position));
            }
        });
        recyclerCoverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                tvImageDescribe.setText((position + 1) + "/" + recyclerCoverFlow.getLayoutManager().getItemCount());
            }
        });
    }

}
