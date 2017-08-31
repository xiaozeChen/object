package com.custom.chenxz.object.view;

import android.app.Activity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.custom.chenxz.object.R;
import com.custom.chenxz.object.wedget.WeChatImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeChatImageViewActivity extends Activity {

    @BindView(R.id.iv_weChat)
    WeChatImageView ivWeChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat_image_view);
        ButterKnife.bind(this);
        String url = "http://192.168.10.148/upload/chatimguser/image/20170829/63802d19621699c6168bdf953c6993bd.jpg";
        Glide.with(this).clear(ivWeChat);
        Glide.with(this).load(url).into(ivWeChat);
//        ivWeChat.setImageResource(R.drawable.blue);
    }

}
