package com.zhiliang.smarttool;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.zhiliang.smarttool.util.NotificationHelp;

public class BackGroundService extends Service {
    private static final int BACK_GROUND_NOTIFICATION_ID = 1;
    private static final String TAG = BackGroundService.class.getSimpleName();
    private BackGroundBinder myIBinder = new BackGroundBinder();

    // 通信数据传输;由里面的方法实现
    public class BackGroundBinder extends Binder {

        public void getBinderData() {
            Toast.makeText(BackGroundService.this, "get", Toast.LENGTH_SHORT).show();
        }

        public void getBinderData2() {
            Toast.makeText(BackGroundService.this, "get2", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intent2 = new Intent(this, SettingsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent2, 0);
        NotificationHelp.creatBackGroundLiveNotification();//创建通道
        Notification notification = new NotificationCompat.Builder(this, NotificationHelp.BACK_GROUND_CHANNEL_ID)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.smart_tool_above_ground))
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();
        startForeground(BACK_GROUND_NOTIFICATION_ID, notification);


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        /*intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);*/
        registerReceiver(BatteryChangedAndScreenReceiver.getINSTANCE(), intentFilter);
//        return super.onStartCommand(intent, flags, startId);
        return Service.START_STICKY;
    }

    // 与活动通信
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myIBinder;
    }
}