package com.zhiliang.smarttool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhiliang.smarttool.util.LogUtil;

public class BatteryChangedAndScreenReceiver extends BroadcastReceiver {
    private static BatteryChangedAndScreenReceiver INSTANCE;

    public static BatteryChangedAndScreenReceiver getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new BatteryChangedAndScreenReceiver();
        return INSTANCE;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.e("action = " + intent.getAction());
        /*if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
            BatteryInfo batteryInfo = new BatteryInfo(intent);
            Intent updateIntent = new Intent(context, UpdateService.class);
            updateIntent.setAction(UpdateService.ACTION_BATTERY_CHANGED);
            batteryInfo.saveToIntent(updateIntent);
            context.startService(updateIntent);
        } else if (Intent.ACTION_BATTERY_LOW.equals(intent.getAction())) {
            Intent updateIntent = new Intent(context, UpdateService.class);
            updateIntent.setAction(UpdateService.ACTION_BATTERY_LOW);
            context.startService(updateIntent);
        } else if (Intent.ACTION_BATTERY_OKAY.equals(intent.getAction())) {
            Intent updateIntent = new Intent(context, UpdateService.class);
            updateIntent.setAction(UpdateService.ACTION_BATTERY_OKAY);
            context.startService(updateIntent);
        }*/
    }
}
