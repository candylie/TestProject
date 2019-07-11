package com.zk.screen_master;

import android.util.Log;

import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.zk.framework.app.ZApp;

/**
 * -
 *
 * @author 张科
 * @date 2019/3/25.
 */
public class ScreenApp extends ZApp {

    @Override
    public void onCreate() {
        super.onCreate();
        SmartShow.init(this);
        SmartToast.complete("ScreenApp创建");
        Log.e("zk", "ScreenApp创建");
    }

}
