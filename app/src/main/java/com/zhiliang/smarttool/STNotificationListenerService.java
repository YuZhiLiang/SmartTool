package com.zhiliang.smarttool;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import java.util.List;

public class STNotificationListenerService extends NotificationListenerService {
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(5);
            boolean startBackGroundService = true;
            for (ActivityManager.RunningServiceInfo runningService : runningServices) {
                if (runningService.service.getClassName().contains(BackGroundService.class.getSimpleName())) {
                    startBackGroundService = false;
                    break;
                }
            }
            if (startBackGroundService) {
                startService(new Intent(this, BackGroundService.class));
            }
        }

    }
}
