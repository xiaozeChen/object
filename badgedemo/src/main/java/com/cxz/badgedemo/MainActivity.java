package com.cxz.badgedemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.utils.badgenumbermanager.BadgeNumberManager;
import com.utils.badgenumbermanager.BadgeNumberManagerXiaoMi;
import com.utils.badgenumbermanager.MobileBrand;

public class MainActivity extends AppCompatActivity {

    private TextView ivMobileBrand;
    private Button btnSetBadge;
    private Button btnClear;
    private NotificationManager notificationManager;
    private Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initView();
        ivMobileBrand.setText("手机品牌：" + Build.MANUFACTURER);
        btnSetBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置应用在桌面上显示的角标,小米机型只要用户点击了应用图标进入应用，会自动清除掉角标
                if (!Build.MANUFACTURER.equalsIgnoreCase(MobileBrand.XIAOMI)) {
                    BadgeNumberManager.from(MainActivity.this).setBadgeNumber(0);
                    Toast.makeText(MainActivity.this, "清除桌面角标成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        notificationManager = (NotificationManager) MainActivity.this.
                getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(MainActivity.this.getApplicationInfo().icon)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("推送标题")
                .setContentText("我是推送内容")
                .setTicker("ticker")
                .setAutoCancel(true)
                .build();
        //设置应用在桌面上显示的角标
        if (Build.MANUFACTURER.equalsIgnoreCase(MobileBrand.HUAWEI) ||
                Build.MANUFACTURER.equalsIgnoreCase(MobileBrand.OPPO) ||
                Build.MANUFACTURER.equalsIgnoreCase(MobileBrand.VIVO)) {
            BadgeNumberManager.from(MainActivity.this).setBadgeNumber(10);
            Toast.makeText(MainActivity.this, "设置桌面角标成功", Toast.LENGTH_SHORT).show();
        } else if (Build.MANUFACTURER.equalsIgnoreCase(MobileBrand.XIAOMI)) {
            setXiaomiBadgeNumber();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//如果是8.0则支持角标
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // The id of the channel.
                String id = "my_channel_01";
                // The user-visible name of the channel.
                CharSequence name = "cxz";
                // The user-visible description of the channel.
                String description = "cxzdemo";
                int importance = NotificationManager.IMPORTANCE_LOW;
                NotificationChannel mChannel = new NotificationChannel(id, name, importance);
                // Configure the notification channel.
                mChannel.setDescription(description);
                mChannel.enableLights(true);
                // Sets the notification light color for notifications posted to this
                // channel, if the device supports this feature.
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mNotificationManager.createNotificationChannel(mChannel);
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // Sets an ID for the notification, so it can be updated.
                int notifyID = 1;
                // The id of the channel.
                String CHANNEL_ID = "my_channel_01";
                // Create a notification and set the notification channel.
                Notification notification = new Notification.Builder(MainActivity.this)
                        .setContentTitle("New Message")
                        .setContentText("You've received new messages.")
                        .setSmallIcon(MainActivity.this.getApplicationInfo().icon)
                        .setChannelId(CHANNEL_ID)
                        .build();
                // Issue the notification.
                mNotificationManager.notify(1111, notification);
            }
        }
    }

    private void initView() {
        ivMobileBrand = findViewById(R.id.iv_mobile_brand);
        btnSetBadge = findViewById(R.id.btn_set_badge);
        btnClear = findViewById(R.id.btn_clear);
    }

    private void setXiaomiBadgeNumber() {
        BadgeNumberManagerXiaoMi.setBadgeNumber(notification, 10);
        notificationManager.notify(1000, notification);
        Toast.makeText(MainActivity.this, "设置桌面角标成功", Toast.LENGTH_SHORT).show();
    }
}