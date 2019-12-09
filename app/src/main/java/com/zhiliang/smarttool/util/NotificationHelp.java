package com.zhiliang.smarttool.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.zhiliang.smarttool.R;
import com.zhiliang.smarttool.STApplication;

import androidx.annotation.RequiresApi;

public class NotificationHelp {
    public static final String BACK_GROUND_CHANNEL_ID = "com.zhiliang.smarttool.background.live";
    private static final String BACK_GROUND_CHANNEL_NAME = "保活通道";

    public static void creatBackGroundLiveNotification() {
        NotificationManager notificationManager = (NotificationManager) STApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {
            notificationManager.createNotificationChannel(getBackGroundLiveChanel());
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private static NotificationChannel getBackGroundLiveChanel() {
        NotificationChannel notificationChannel = new NotificationChannel(BACK_GROUND_CHANNEL_ID,
                BACK_GROUND_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription(STApplication.getInstance().getString(R.string.back_ground_live_channel_des));
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        return notificationChannel;
    }
}
