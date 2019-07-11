package com.zk.screen_master.receiver;


import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.toast.SmartToast;

import java.util.Objects;

/**
 * -开机广播监听
 *
 * @author 张科
 * @date 2019/3/25.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SmartShow.init((Application) context.getApplicationContext());
        Log.e("zk", "开机广播监听,intent=" + Objects.requireNonNull(intent.getExtras()).toString());
        SmartToast.complete("开机广播监听,intent=" + Objects.requireNonNull(intent.getExtras()).toString());

    }
}
