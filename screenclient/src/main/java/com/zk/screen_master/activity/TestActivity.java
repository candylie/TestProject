package com.zk.screen_master.activity;

import android.content.Intent;
import android.view.View;

import com.zk.framework.ui.ZBaseActivity;
import com.zk.screen_master.R;
import com.zk.screen_master.receiver.LockScreenReceiver;
import com.zk.screen_master.service.LockScreenService;

import butterknife.OnClick;

/**
 * -测试界面
 *
 * @author 张科
 * @date 2019/3/25.
 */
public class TestActivity extends ZBaseActivity {


    @Override
    public String getActivityName() {
        return "锁屏测试界面";
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_test_screen_activity;
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

    @OnClick({R.id.button, R.id.button2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                startService(new Intent(this, LockScreenService.class));
                break;
            case R.id.button2:
                stopService(new Intent(this, LockScreenService.class));
                break;
            default:
                break;
        }
    }
}
