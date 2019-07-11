package com.zk.screen_master.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zk.screen_master.ScreenApp;
import com.zk.screen_master.activity.LockScreenActivity;

import java.util.Objects;

/**
 * -监听屏幕关闭的广播接收器
 *
 * @author 张科
 * @date 2019/3/25.
 */
public class LockScreenReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        //判断服务是否在运行中
        ScreenApp app = (ScreenApp) context.getApplicationContext();
        if (Objects.equals(intent.getAction(), Intent.ACTION_SCREEN_OFF)) {
            Intent intent1 = new Intent(context, LockScreenActivity.class);
            intent1.putExtra("close", true);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            app.startActivity(intent1);
        } else if (Objects.equals(intent.getAction(), Intent.ACTION_USER_PRESENT)) {
//            if (ZApp.getNowActivity() instanceof LockScreenActivity) {
//                ZApp.getNowActivity().finish();
//            }
        }
    }
}
