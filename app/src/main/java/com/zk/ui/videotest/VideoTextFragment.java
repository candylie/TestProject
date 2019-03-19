package com.zk.ui.videotest;

import com.zk.framework.ui.ZBaseFragment;
import com.zk.framework.ui.mvp.ZBasePresenter;
import com.zk.mytest.R;

import org.jetbrains.annotations.NotNull;

/**
 * -
 *
 * @author 张科
 * @date 2019/3/19.
 */
public class VideoTextFragment extends ZBaseFragment {
    @NotNull
    @Override
    public String getFragmentName() {
        return "";
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_video_test_fragment;
    }

    @Override
    public void setPresenter(ZBasePresenter presenter) {

    }
}
