package com.zk.ui.home;

import android.view.View;

import com.zk.framework.ui.mvp.ZBasePresenter;
import com.zk.framework.ui.mvp.ZBaseView;
import com.zk.mytest.R;
import com.zk.ui.main.CommonActivity;
import com.zk.ui.publish.DemandPublishFragment;
import com.zk.util.IntentUtil;

/**
 * -主页界面Presenter
 *
 * @author 张科
 * @date 2019/2/28.
 */
public class HomePagePresenter implements ZBasePresenter {

    private ZBaseView mView;

    public HomePagePresenter(ZBaseView mView) {
        this.mView = mView;
    }

    @Override
    public void registerListener(int flag, View view) {

    }

    @Override
    public void triggerViewListener(int flag, View view) {
        switch (view.getId()) {
            case R.id.home_task1_tv:

                IntentUtil.Build.newInstance().setContext(mView.getAttachAcitvity())
                        .setToActivity(CommonActivity.class)
                        .putExtra(CommonActivity.FRAGMENT_PATH, DemandPublishFragment.class.getName())
                        .start();
                break;
            case R.id.home_task2_tv:
                break;
            default:
                break;
        }

    }
}
