package com.zk.screen_master.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zk.screen_master.receiver.LockScreenReceiver;

/**
 * -监听屏幕开关状态的后台服务
 *
 * @author 张科
 * @date 2019/3/25.
 */
public class LockScreenService extends Service {
    private final String TAG = "LockScreenService_zk_";

    private boolean isLive = false;
    private LockScreenReceiver receiver;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand,flags=" + flags + ",startID=" + startId);
        reg();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 主动启动服务
     *
     * @param service -
     * @return -
     */
    @Override
    public ComponentName startService(Intent service) {
        Log.e(TAG, "startService");
        reg();
        return super.startService(service);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        reg();
        return null;
    }


    @Override
    public boolean stopService(Intent name) {
        Log.e(TAG, "stopService");
        isLive = false;
        unReg();
        return super.stopService(name);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.e(TAG, "unbindService");
    }

    public boolean isLive() {
        return isLive;
    }

    private void reg() {
        if (!isLive) {
            isLive = true;
            receiver = new LockScreenReceiver();
            IntentFilter mIntentFilter = new IntentFilter();
            mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
            mIntentFilter.addAction(Intent.ACTION_SCREEN_ON);
            mIntentFilter.addAction(Intent.ACTION_USER_PRESENT);
            mIntentFilter.setPriority(Integer.MAX_VALUE);
            registerReceiver(receiver, mIntentFilter);
        }
    }

    private void unReg() {
        isLive = false;
        unregisterReceiver(receiver);
    }
}
