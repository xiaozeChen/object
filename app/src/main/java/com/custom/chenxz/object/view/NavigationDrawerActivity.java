package com.custom.chenxz.object.view;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.custom.chenxz.object.R;
import com.custom.chenxz.object.databean.ImageProvider;
import com.custom.chenxz.object.server.VideoLiveWallpaper;
import com.custom.chenxz.photolibrary.controller.PhotoPagerConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_android7)
    Button btnAndroid7;
    @BindView(R.id.cb_voice)
    CheckBox cbVoice;
    @BindView(R.id.btn_LiveWallPaper)
    Button btnLiveWallPaper;
    @BindView(R.id.btn_NestedScroll)
    Button btnNestedScroll;
    @BindView(R.id.btn_RvInSV)
    Button btnRvInSV;
    @BindView(R.id.btn_ExpandableListView)
    Button btnExpandableListView;
    @BindView(R.id.btn_PhotoViewer)
    Button btnPhotoViewer;
    @BindView(R.id.btn_FlowTab)
    Button btnFlowTab;
    @BindView(R.id.btn_ColorImageView)
    Button btnColorImageView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.btn_CustomViewPager)
    Button btnCustomViewPager;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        bind = ButterKnife.bind(this);
        initView();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
    }

    private void initView() {
        cbVoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 静音
                    VideoLiveWallpaper.voiceSilence(getApplicationContext());
                } else {
                    VideoLiveWallpaper.voiceNormal(getApplicationContext());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick({R.id.btn_PhotoViewer, R.id.btn_android7, R.id.btn_LiveWallPaper, R.id.fab, R.id.btn_CustomViewPager,
            R.id.btn_NestedScroll, R.id.btn_RvInSV, R.id.btn_ExpandableListView, R.id.btn_FlowTab, R.id.btn_ColorImageView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.btn_android7:
                startActivity(new Intent(this, TakePhotoActivity.class));
                break;
            case R.id.btn_LiveWallPaper:
                VideoLiveWallpaper.setToWallPaper(this);
                break;
            case R.id.btn_NestedScroll:
                startActivity(new Intent(this, NestedScrollActivity.class));
                break;
            case R.id.btn_RvInSV:
                startActivity(new Intent(this, NestRecycleViewInScrollViewActivity.class));
                break;
            case R.id.btn_ExpandableListView:
                startActivity(new Intent(this, ExpandableListActivity.class));
                break;
            case R.id.btn_FlowTab:
                startActivity(new Intent(this, FlowActivity.class));
                break;
            case R.id.btn_ColorImageView:
                startActivity(new Intent(this, ColorViewActivity.class));
            case R.id.btn_CustomViewPager:
                startActivity(new Intent(this, CustomFragmentActivity.class));
                break;
            case R.id.btn_PhotoViewer:
                new PhotoPagerConfig.Builder(this)
                        .setBigImageUrls(ImageProvider.getBigImgUrls())
                        .setLowImageUrls(ImageProvider.getLowImgUrls())
                        .setSavaImage(true)
                        .build();
                break;
        }
    }

    public static void setToWallPaper(Context context) {
        final Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(context, VideoLiveWallpaper.class));
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
            bind = null;
        }
    }
}
