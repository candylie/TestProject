package com.zk.framework.view.videoplay;

import com.zk.framework.view.videoplay.mask.IZBaseVideoFunMaskView;
import com.zk.framework.view.videoplay.util.ZVideoPlayMediaManager;

import static com.zk.framework.view.videoplay.constant.ZVideoConstant.ERROR_PLAY_STATE;
import static com.zk.framework.view.videoplay.constant.ZVideoConstant.FINISHED_PLAY_STATE;
import static com.zk.framework.view.videoplay.constant.ZVideoConstant.PAUSE_PLAY_STATE;
import static com.zk.framework.view.videoplay.constant.ZVideoConstant.PLAYING_PLAY_STATE;
import static com.zk.framework.view.videoplay.constant.ZVideoConstant.PREPARING_PLAY_STATE;

/**
 * -播放器工具类,用来控制功能蒙版的状态, 播放器的播放暂停重播 快进转跳等操作的工具,对包外隐藏
 *
 * @author 张科
 * @date 2019/3/18.
 */
class ZVideoPlayUtil {

    /**
     * 准备播放
     *
     * @param mViewShowState -当前播放界面的展示状态
     * @param videoView      -
     */
    static void readyPlay(int mViewShowState, ZVideoView videoView) {
        //设置播放器当前播放状态
        videoView.setPlayState(PREPARING_PLAY_STATE);
        showMaskBarView(mViewShowState, PREPARING_PLAY_STATE, videoView);
        //延时消失功能蒙版View
        videoView.delayHideMaskView();
        //开始播放视频
        ZVideoPlayMediaManager.obtain().readyPlay(videoView, videoView.getBuilder());
    }

    /**
     * 开始播放
     *
     * @param mViewShowState -
     * @param videoView      -
     */
    static void start(int mViewShowState, ZVideoView videoView) {
        //设置播放器当前播放状态
        videoView.setPlayState(PLAYING_PLAY_STATE);
        showMaskBarView(mViewShowState, PLAYING_PLAY_STATE, videoView);
        //延时消失功能蒙版View
        videoView.delayHideMaskView();
        ZVideoPlayMediaManager.obtain().start(videoView);
    }

    /**
     * 暂停播放
     *
     * @param mViewShowState -
     * @param videoView      -
     */
    static void pause(int mViewShowState, ZVideoView videoView) {
        //设置播放器当前播放状态
        videoView.setPlayState(PAUSE_PLAY_STATE);
        showMaskBarView(mViewShowState, PAUSE_PLAY_STATE, videoView);
        //延时消失功能蒙版View
        videoView.delayHideMaskView();
        //开始暂停视频
        ZVideoPlayMediaManager.obtain().pause(videoView);
    }

    /**
     * 重播
     *
     * @param mViewShowState -
     * @param videoView      -
     */
    static void replay(int mViewShowState, ZVideoView videoView) {
        videoView.setPlayState(PLAYING_PLAY_STATE);
        hideMaskBarView(PLAYING_PLAY_STATE, videoView);
        //延时消失功能蒙版View
        videoView.delayHideMaskView();
        //开始重头播放视频
        ZVideoPlayMediaManager.obtain().replay(videoView);
    }

    /**
     * 播放失败
     *
     * @param mViewShowState -
     * @param videoView      -
     */
    static void playError(int mViewShowState, ZVideoView videoView) {
        videoView.setPlayState(ERROR_PLAY_STATE);
        showMaskBarView(mViewShowState, ERROR_PLAY_STATE, videoView);
    }

    /**
     * 播放完毕
     *
     * @param videoView -
     */
    static void playCompletion(ZVideoView videoView) {
        videoView.setPlayState(FINISHED_PLAY_STATE);
        hideMaskBarView(FINISHED_PLAY_STATE, videoView);
    }

    /**
     * 展示蒙版顶部 底部bar
     *
     * @param mViewShowState  -
     * @param mVideoPlayState -
     * @param videoView       -
     */
    static void showMaskBarView(int mViewShowState, int mVideoPlayState, ZVideoView videoView) {
        IZBaseVideoFunMaskView maskView = videoView.getMaskView();
        if (maskView != null) {
            maskView.changePlayState(mVideoPlayState);
            maskView.showBarView(mViewShowState);
        }
        videoView.delayHideMaskView();
    }

    /**
     * 展示蒙版的中间的操作按钮
     *
     * @param mViewShowState  -
     * @param mVideoPlayState -
     * @param videoView       -
     */
    static void showMaskStateBTView(int mViewShowState, int mVideoPlayState, ZVideoView videoView) {
        IZBaseVideoFunMaskView maskView = videoView.getMaskView();
        if (maskView != null) {
            maskView.changePlayState(mVideoPlayState);
            maskView.showStateChangeButton();
        }
        videoView.delayHideMaskView();
    }

    /**
     * 关闭蒙版顶部 底部bar
     *
     * @param mVideoPlayState -
     * @param videoView       -
     */
    static void hideMaskBarView(int mVideoPlayState, ZVideoView videoView) {
        IZBaseVideoFunMaskView maskView = videoView.getMaskView();
        if (maskView != null) {
            maskView.changePlayState(mVideoPlayState);
            maskView.hideBarView();
        }
    }

    /**
     * 关闭蒙版的中间的操作按钮
     *
     * @param mViewShowState  -
     * @param mVideoPlayState -
     * @param videoView       -
     */
    static void hideMaskStateBTView(int mViewShowState, int mVideoPlayState, ZVideoView videoView) {
        IZBaseVideoFunMaskView maskView = videoView.getMaskView();
        if (maskView != null) {
            maskView.changePlayState(mVideoPlayState);
            maskView.hideStateChangeButton();
        }
    }
}
