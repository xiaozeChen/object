package com.custom.chenxz.object.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.custom.chenxz.object.R;
import com.custom.chenxz.object.view.fragment.EventTestFragment;
import com.custom.chenxz.object.view.fragment.GravityFragment;
import com.custom.chenxz.object.view.fragment.LimitSelectedFragment;
import com.custom.chenxz.object.view.fragment.ListViewTestFragment;
import com.custom.chenxz.object.view.fragment.ScrollViewTestFragment;
import com.custom.chenxz.object.view.fragment.SimpleFragment;
import com.custom.chenxz.object.view.fragment.SingleChooseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlowActivity extends AppCompatActivity {

    @BindView(R.id.id_tablayout)
    TabLayout idTablayout;
    @BindView(R.id.id_viewpager)
    ViewPager idViewpager;
    @BindView(R.id.main_content)
    LinearLayout mainContent;
    private String[] mTabTitles = new String[]
            {"Muli Selected", "Limit 3",
                    "Event Test", "ScrollView Test", "Single Choose", "Gravity", "ListView Sample"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        ButterKnife.bind(this);

        idViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i) {
                    case 0:
                        return new SimpleFragment();
                    case 1:
                        return new LimitSelectedFragment();
                    case 2:
                        return new EventTestFragment();
                    case 3:
                        return new ScrollViewTestFragment();
                    case 4:
                        return new SingleChooseFragment();
                    case 5:
                        return new GravityFragment();
                    case 6:
                        return new ListViewTestFragment();
                    default:
                        return new EventTestFragment();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {

                return mTabTitles[position];
            }

            @Override
            public int getCount() {
                return mTabTitles.length;
            }
        });


        idTablayout.setupWithViewPager(idViewpager);
    }

}
