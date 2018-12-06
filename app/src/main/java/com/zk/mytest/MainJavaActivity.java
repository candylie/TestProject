package com.zk.mytest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

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
        setContentView(R.layout.activity_main);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            //检查权限
            int i = ActivityCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就申请权限
                ActivityCompat.requestPermissions(this, permissions, 321);
            }
        }

        //测试权限申请状态
//        ApplicationInfo info = new ApplicationInfo();
//        @SuppressLint("HardwareIds") String id = ((TelephonyManager) Objects.requireNonNull(this.getSystemService(Context.TELEPHONY_SERVICE))).getDeviceId();


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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("zk", "requestCode=" + requestCode + ",permissions=" + Arrays.toString(permissions) + ",grantResults=" + Arrays.toString(grantResults));
    }
}
