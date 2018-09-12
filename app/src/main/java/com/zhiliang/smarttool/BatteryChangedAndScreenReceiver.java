package com.zhiliang.smarttool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Vibrator;

import com.zhiliang.smarttool.util.SPUtil;
import com.zhiliang.smarttool.util.ToastUtil;

import java.util.concurrent.TimeUnit;

public class BatteryChangedAndScreenReceiver extends BroadcastReceiver {
    private static BatteryChangedAndScreenReceiver INSTANCE;
    public static final String S_LOW_BATTERY_WARN_VALUE_KEY = "low_battery_warn_value";//低电量提醒边界值的Key
    public static final int S_LOW_BATTERY_WARN_VALUE = 3;//默认的低电量提醒边界值

    public static final String S_USER_RESUME_TIME_KEY = "user_resume_time";//用户最近一次亮屏或解锁的时间
    public static final String S_LAST_REMIND_ALERT_TIME_KEY = "last_remind_alert_time";//最后一次提醒弹出的时间
    public static final int S_DEFAULT_REMINDER_INTERVAL_TIME = 30;//两次低电量提醒的默认时间间隔

    private BatteryChangedAndScreenReceiver() {
    }

    public static BatteryChangedAndScreenReceiver getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new BatteryChangedAndScreenReceiver();
        return INSTANCE;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //最后一次提醒弹出的时间
        long lastRemindAlertTime = SPUtil.getINSTANCE().getLong(S_LAST_REMIND_ALERT_TIME_KEY, 0);
        if (System.currentTimeMillis() - lastRemindAlertTime <= TimeUnit.SECONDS.toMillis(S_DEFAULT_REMINDER_INTERVAL_TIME))
            return;

        //用户最后一次解锁或者亮屏的时间
        long userResumeTime = SPUtil.getINSTANCE().getLong(S_USER_RESUME_TIME_KEY, 0);
        if (System.currentTimeMillis() - userResumeTime <= TimeUnit.SECONDS.toMillis(S_DEFAULT_REMINDER_INTERVAL_TIME))
            return;

        String action = intent.getAction();
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
        //记录最后一次弹出提醒的时间
        SPUtil.getINSTANCE().putLong(S_LAST_REMIND_ALERT_TIME_KEY, System.currentTimeMillis());

        //是否需要震动提醒
        if (SPUtil.getINSTANCE().getBoolean(context.getString(R.string.low_battery_reminder_vibrate_key))) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(2000);
            }
        }

        //获取提醒的样式
        String remindMode = SPUtil.getINSTANCE().getString(context.getString(R.string.low_battery_remind_mode_key), context.getString(R.string.default_remind_mode));
        if (remindMode.equals(context.getString(R.string.remind_mode_dialog))) {
            doDialogRemind(context);
        } else if (remindMode.equals(context.getString(R.string.remind_mode_toast))) {
            doToastRemind();
        }
    }

    /**
     * 吐司提醒
     */
    private void doToastRemind() {
        ToastUtil.toast(STApplication.getInstance().getString(R.string.low_battery_go_charge));
    }

    /**
     * 弹窗提醒
     *
     * @param context
     */
    private void doDialogRemind(Context context) {
        Intent intent = new Intent(context, RemindActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
