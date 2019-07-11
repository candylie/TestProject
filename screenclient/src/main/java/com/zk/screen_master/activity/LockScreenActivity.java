package com.zk.screen_master.activity;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.zk.framework.ui.ZBaseActivity;
import com.zk.screen_master.R;

import butterknife.OnClick;

/**
 * -
 *
 * @author 张科
 * @date 2019/3/25.
 */
public class LockScreenActivity extends ZBaseActivity {

    private boolean isShowing = false;

    @Override
    public String getActivityName() {
        return "锁屏界面";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isShowing) {
            return;
        }
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_close_screen_activity;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void findView() {

    }

    @Override
    public void initObject() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @OnClick(R.id.button3)
    public void onClick(View v) {
        if (v.getId() == R.id.button3) {
            SmartToast.complete("关闭");
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    keyguardManager.requestDismissKeyguard(this, new KeyguardManager.KeyguardDismissCallback() {
                        @Override
                        public void onDismissError() {
                            super.onDismissError();
                            isShowing = false;
                        }

                        @Override
                        public void onDismissSucceeded() {
                            super.onDismissSucceeded();
                            isShowing = true;
                            finish();
                        }

                        @Override
                        public void onDismissCancelled() {
                            super.onDismissCancelled();
                            isShowing = false;
                        }
                    });
                } else {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                    window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                    KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock("CustomLockScreen");
                    lock.disableKeyguard();
                    isShowing = true;
                    finish();
                }
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
