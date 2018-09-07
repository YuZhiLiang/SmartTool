package com.zhiliang.smarttool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.zhiliang.smarttool.util.LogUtil;
import com.zhiliang.smarttool.util.SPUtil;
import com.zhiliang.smarttool.util.ToastUtil;

public class BatteryChangedAndScreenReceiver extends BroadcastReceiver {
    private static BatteryChangedAndScreenReceiver INSTANCE;
    public static final String S_LOW_BATTERY_WARN_VALUE_KEY = "low_battery_warn_value";//低电量提醒边界值的Key
    public static final int S_LOW_BATTERY_WARN_VALUE = 3;//默认的低电量提醒边界值

    public static final String S_USER_RESUME_TIME_KEY = "user_resume_time";//用户最近一次亮屏或解锁的时间

    public static BatteryChangedAndScreenReceiver getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new BatteryChangedAndScreenReceiver();
        return INSTANCE;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtil.e("action = " + action);
        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {//电量发生变化
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);//获取充电状态
            if (status == BatteryManager.BATTERY_STATUS_DISCHARGING || status == BatteryManager.BATTERY_STATUS_UNKNOWN) {//不是充电状态
                int boundaryValue = SPUtil.getINSTANCE().getInt(S_LOW_BATTERY_WARN_VALUE_KEY, S_LOW_BATTERY_WARN_VALUE);//电量低于多少的边界值
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);//现在有多少电量
//                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1); //电量的总刻度
                if (level == boundaryValue) {
                    alert(context);
                }
            }
        } else if (Intent.ACTION_BATTERY_LOW.equals(action)) {//系统的电量低警告
            doToastRemind();
        } else if (Intent.ACTION_SCREEN_ON.equals(action) || Intent.ACTION_USER_PRESENT.equals(action)) {//亮屏幕或者解锁
            SPUtil.getINSTANCE().putLong(S_USER_RESUME_TIME_KEY, System.currentTimeMillis());//更新用户最近一次亮屏或解锁的时间
        }
    }

    private void alert(Context context) {
        int remindMode = SPUtil.getINSTANCE().getInt(SettingsActivity.S_REMINDER_MODE_KEY, SettingsActivity.S_REMINDER_MODE_TOAST);
        if (remindMode == SettingsActivity.S_REMINDER_MODE_DIALOG) {
            doDialogRemind(context);
        } else if (remindMode == SettingsActivity.S_REMINDER_MODE_TOAST) {
            doToastRemind();
        }
    }

    private void doToastRemind() {
        ToastUtil.toast(STApplication.getInstance().getString(R.string.low_battery_go_charge));
    }

    private void doDialogRemind(Context context) {
        /*new AlertDialog.Builder(context).setMessage(R.string.low_battery_go_charge)
                .setPositiveButton(R.string.confrim, null).create().show();*/
        Intent intent = new Intent(context, RemindActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
