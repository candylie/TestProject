package com.zk.ui.main;

import com.coder.zzq.smartshow.core.SmartShow;
import com.zk.framework.app.ZApp;

/**
 * -
 *
 * @author 张科
 * @date 2019/2/28.
 */
public class MyApp extends ZApp {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化toast
        SmartShow.init(this);

    }
}
