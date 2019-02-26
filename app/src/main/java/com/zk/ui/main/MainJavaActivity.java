package com.zk.ui.main;

import android.Manifest;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.zk.framework.util.permissions.PermissionsCall;
import com.zk.framework.util.permissions.PermissionsUtil;
import com.zk.mytest.R;

import java.util.Arrays;

/**
 * -
 *
 * @author 张科
 * @date 2018/12/5.
 */
public class MainJavaActivity extends AppCompatActivity {
    /**
     * 需要申请的权限
     */
    private String[] permissions = {Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PermissionsUtil.create().setCallBack(new PermissionsCall() {

            @Override
            public void granted() {
                Log.e("zk", "granted ");
                //测试权限申请状态
//                @SuppressLint({"HardwareIds", "MissingPermission"}) String id = ((TelephonyManager) Objects.requireNonNull(MainJavaActivity.this.getSystemService(Context.TELEPHONY_SERVICE))).getDeviceId();
            }

            @Override
            public void fail(String[] permissionsArray) {
                Log.e("zk", "fail permissionsArray=" + Arrays.toString(permissionsArray));

            }

            @Override
            public void needShowRationale(String[] permissionsArray) {
                Log.e("zk", "needShowRationale permissionsArray=" + Arrays.toString(permissionsArray));

            }
        }).request(this, permissions);


        WindowManager.LayoutParams p;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            p = new WindowManager.LayoutParams(300,
                    100,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSPARENT);
        } else {
            p = new WindowManager.LayoutParams(300,
                    100,
                    WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSPARENT);
        }
        p.gravity = Gravity.CENTER;

        Button bt = new Button(this);
        bt.setText("BT");
        bt.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        getWindowManager().addView(bt, p);
    }
}
