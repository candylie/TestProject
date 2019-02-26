package com.zk.framework.util;

import android.widget.Toast;

import com.zk.framework.app.ZApp;

/**
 * -
 *
 * @author 张科
 * @date 2019/2/26.
 */
public class MyToast {

    public static void toast(String contetn, int time) {
        Toast.makeText(ZApp.getNowActivity(), contetn, time).show();
    }

    public static void toast(String contetn) {
        Toast.makeText(ZApp.getNowActivity(), contetn, Toast.LENGTH_SHORT).show();
    }
}
