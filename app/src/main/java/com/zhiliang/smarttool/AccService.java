package com.zhiliang.smarttool;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.zhiliang.smarttool.publicpage.MainActivity;
import com.zhiliang.smarttool.util.AccessibilityUtil;
import com.zhiliang.smarttool.util.SPUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class AccService extends AccessibilityService {
    private final String TAG = AccService.class.getSimpleName();
    private static final int DELAY_PAGE = 320; // 页面切换时间
    public static final String mContentDescriptionKey = "ContentDescriptionKey";
    private NotificationManager mNotifyManager;
    private Disposable mSubscribe;
    private Disposable mDoActionBackSubscribe;
    private String mChannelId = "SmartTool001";
    private String mChannelName = "ChatRemind";
    private long mServiceConnectTimeStamp = 0;
    public static boolean sActionBack = false;
    private static Disposable sActionBackDisposable;

    public static void setActionBack(boolean actionBack) {
        sActionBack = actionBack;
        if (sActionBack) {
            if (sActionBackDisposable != null && !sActionBackDisposable.isDisposed()) {
                sActionBackDisposable.dispose();
            }
            sActionBackDisposable = Observable.timer(5, TimeUnit.MINUTES).subscribe(aLong -> sActionBack = false);
        } else {
            if (sActionBackDisposable != null && !sActionBackDisposable.isDisposed()) {
                sActionBackDisposable.dispose();
            }
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (mNotifyManager == null) {
            mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);//获取状态栏通知的管理类（负责发通知、清除通知等操作）
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(mChannelId, mChannelName, NotificationManager.IMPORTANCE_HIGH);
                channel.enableLights(true); //是否在桌面icon右上角展示小红点
                channel.setLightColor(Color.GREEN); //小红点颜色
                channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                mNotifyManager.createNotificationChannel(channel);
            }
        }
        if (event != null) {
            if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
                CharSequence contentDescription = event.getContentDescription();
                if (!TextUtils.isEmpty(contentDescription)) {
                    if (contentDescription.toString()
                            .contains(SPUtil.getINSTANCE().getString(mContentDescriptionKey, "当前所在页面,与今天打卡了吗❔（36D小\uD83D\uDC37 \uD83D\uDC37 ）的聊天"))) {
                        Log.e("yzl", "就是这个页面！");
                        mNotifyManager.cancelAll();
                        if (mSubscribe != null && !mSubscribe.isDisposed()) mSubscribe.dispose();
                        mSubscribe = Observable.timer(10, TimeUnit.MINUTES)
                                .subscribe(aLone -> {
                                    not();
                                });
                    }
                }
            }

            back2AppPage(event);

            Log.e("yzl", event.toString());
        } else {
            Log.e("yzl", "event is null");
        }
    }

    /**
     * 模拟返回键回到App页面
     *
     * @param event
     */
    @SuppressLint("CheckResult")
    private void back2AppPage(AccessibilityEvent event) {
        if (mServiceConnectTimeStamp > 0) {
            if (System.currentTimeMillis() - mServiceConnectTimeStamp <= 1000) {
                mServiceConnectTimeStamp = 0;
                if (!sActionBack) return;
                if (mDoActionBackSubscribe != null && !mDoActionBackSubscribe.isDisposed()) return;
                if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
                        && event.getContentChangeTypes() == AccessibilityEvent.CONTENT_CHANGE_TYPE_SUBTREE
                        && event.getPackageName().equals(getString(R.string.android_setting_package_name))) {
                    mDoActionBackSubscribe = Observable.just(performGlobalAction(GLOBAL_ACTION_BACK))
                            .delay(DELAY_PAGE, TimeUnit.MILLISECONDS)
                            .map((aBoolean -> {
                                return performGlobalAction(GLOBAL_ACTION_BACK);
                            })).delay(DELAY_PAGE, TimeUnit.MILLISECONDS)
                            .subscribe(aBoolean -> performGlobalAction(GLOBAL_ACTION_BACK));
                }
            } else {
                mServiceConnectTimeStamp = 0;
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        mServiceConnectTimeStamp = System.currentTimeMillis();
        Log.i(TAG, "onServiceConnected: ");
        Toast.makeText(this, getString(R.string.chat_remind) + "开启了", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        Toast.makeText(this, getString(R.string.chat_remind) + "停止了，请重新开启", Toast.LENGTH_LONG).show();
        // 服务停止，重新进入系统设置界面
        AccessibilityUtil.jumpToSetting(this);
    }

    private final int resCode = 1;
    private final int id = 2;

    public void not() {
        //第一步：实例化通知栏构造器Notification.Builder：
        Notification.Builder builder = new Notification.Builder(this);//实例化通知栏构造器Notification.Builder，参数必填（Context类型），为创建实例的上下文
        //第二步：获取状态通知栏管理：
//        NotificationManager mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);//获取状态栏通知的管理类（负责发通知、清除通知等操作）
        //第三步：设置通知栏PendingIntent（点击动作事件等都包含在这里）:
        Intent push = new Intent(this, MainActivity.class);//新建一个显式意图，第一个参数 Context 的解释是用于获得 package name，以便找到第二个参数 Class 的位置
        //PendingIntent可以看做是对Intent的包装，通过名称可以看出PendingIntent用于处理即将发生的意图，而Intent用来用来处理马上发生的意图
        //本程序用来响应点击通知的打开应用,第二个参数非常重要，点击notification 进入到activity, 使用到pendingIntent类方法，PengdingIntent.getActivity()的第二个参数，即请求参数，实际上是通过该参数来区别不同的Intent的，如果id相同，就会覆盖掉之前的Intent了
        PendingIntent contentIntent = PendingIntent.getActivity(this, resCode, push, PendingIntent.FLAG_CANCEL_CURRENT);
        //第四步：对Builder进行配置：
        builder.setContentTitle("快找小猪聊天！")//标题
                .setContentText("赶紧去!")// 详细内容
                .setContentIntent(contentIntent)//设置点击意图
                .setTicker("有没有回小猪消息？")//第一次推送，角标旁边显示的内容
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.timg))//设置大图标
                .setDefaults(Notification.DEFAULT_ALL);//打开呼吸灯，声音，震动，触发系统默认行为
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(mChannelId);
        }
        //.setLights(Color.YELLOW, 300, 0)//单独设置呼吸灯，一般三种颜色：红，绿，蓝，经测试，小米支持黄色
        //.setSound(url)//单独设置声音
        //.setVibrate(new long[] { 100, 250, 100, 250, 100, 250 })//单独设置震动
        //比较手机sdk版本与Android 5.0 Lollipop的sdk
        builder
                /*android5.0加入了一种新的模式Notification的显示等级，共有三种：
                VISIBILITY_PUBLIC只有在没有锁屏时会显示通知
                VISIBILITY_PRIVATE任何情况都会显示通知
                VISIBILITY_SECRET在安全锁和没有锁屏的情况下显示通知*/
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setPriority(Notification.PRIORITY_MAX)//设置该通知优先级
                .setCategory(Notification.CATEGORY_MESSAGE)//设置通知类别
                //.setColor(context.getResources().getColor(R.color.small_icon_bg_color))//设置smallIcon的背景色
                .setFullScreenIntent(contentIntent, true)//将Notification变为悬挂式Notification
                .setSmallIcon(R.drawable.timg);//设置小图标
        //第五步：发送通知请求：
        Notification notify = builder.build();//得到一个Notification对象
        mNotifyManager.cancelAll();
        mNotifyManager.notify(id, notify);//发送通知请求
    }
}
