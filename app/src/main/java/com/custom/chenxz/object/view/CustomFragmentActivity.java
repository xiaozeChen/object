package com.custom.chenxz.object.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.custom.chenxz.object.R;
import com.custom.chenxz.object.view.fragment.VpSimpleFragment;
import com.custom.chenxz.object.wedget.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomFragmentActivity extends FragmentActivity {
    @BindView(R.id.id_indicator)
    ViewPagerIndicator mIndicator;
    @BindView(R.id.id_vp)
    ViewPager mViewPager;
    private List<Fragment> mTabContents = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;
    private List<String> mDatas = Arrays.asList("短信1", "短信2", "短信3", "短信4",
            "短信5", "短信6", "短信7", "短信8", "短信9");
    private List<Integer> colors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.vp_indicator);
        ButterKnife.bind(this);
        initDatas();
        //设置Tab上的标题
        mIndicator.setTabItemTitles(mDatas);
        mIndicator.setmTabColors(colors);
        mViewPager.setAdapter(mAdapter);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager, 0);
    }

    private void initDatas() {
        colors = Arrays.asList(ContextCompat.getColor(this, R.color.color1),
                ContextCompat.getColor(this, R.color.color2),
                ContextCompat.getColor(this, R.color.color3),
                ContextCompat.getColor(this, R.color.color4),
                ContextCompat.getColor(this, R.color.color5),
                ContextCompat.getColor(this, R.color.color6),
                ContextCompat.getColor(this, R.color.color7),
                ContextCompat.getColor(this, R.color.color8),
                ContextCompat.getColor(this, R.color.color9));
        for (String data : mDatas) {
            VpSimpleFragment fragment = VpSimpleFragment.newInstance(data);
            mTabContents.add(fragment);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }
        };
    }


}
