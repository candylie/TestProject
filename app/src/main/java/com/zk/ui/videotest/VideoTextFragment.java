package com.zk.ui.videotest;

import com.zk.framework.ui.ZBaseFragment;
import com.zk.framework.ui.mvp.ZBasePresenter;
import com.zk.framework.view.videoplay.ZVideoView;
import com.zk.framework.view.videoplay.util.ZVideoPlayControl;
import com.zk.mytest.R;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

/**
 * -
 *
 * @author 张科
 * @date 2019/3/19.
 */
public class VideoTextFragment extends ZBaseFragment {

    @BindView(R.id.test_z_video)
    public ZVideoView videoView;

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
    public void initObject() {
        super.initObject();
        String pt = "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4";
        ZVideoPlayControl.ZVideoParamBuilder builder = ZVideoPlayControl.ZVideoParamBuilder.obtain().setVideoURL(pt);
        ZVideoPlayControl.setBuild(videoView, builder);
    }

    @Override
    public void setPresenter(ZBasePresenter presenter) {

    }
}
