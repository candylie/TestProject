package com.zk.framework.view.videoplay;

import android.media.MediaPlayer;

import com.zk.framework.view.videoplay.mask.IZBaseVideoFunMaskView;
import com.zk.framework.view.videoplay.util.ZVideoPlayMediaManager;

import static com.zk.framework.view.videoplay.constant.ZVideoConstant.*;

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
        IZBaseVideoFunMaskView maskView = videoView.getMaskView();
        //设置功能蒙版的状态
        if (maskView != null) {
            if (mViewShowState == NARROW_SHOW_STATE) {
                //当前是竖屏
                maskView.showNarrowStateFunView();
            } else if (mViewShowState == MAXIMUM_SHOW_STATE) {
                //当前是横屏
                maskView.showMaximumStateFunView();
            } else if (mViewShowState == FLOATING_SHOW_STATE) {
                //当前是悬浮
                maskView.showFloatingStateFunView();
            }
            maskView.changePlayState(PREPARING_PLAY_STATE);
        }
        //设置播放器当前播放状态
        videoView.setPlayState(PREPARING_PLAY_STATE);
        //延时消失功能蒙版View
        videoView.delayHideMaskView();
        //开始播放视频
        ZVideoPlayMediaManager.obtain().readyPlay(videoView, videoView.getBuilder());
    }

    /**
     * 开始播放
     *
     * @param mViewShowState -
     * @param mp             -
     * @param videoView      -
     */
    static void start(int mViewShowState, MediaPlayer mp, ZVideoView videoView) {
        IZBaseVideoFunMaskView maskView = videoView.getMaskView();
        //设置功能蒙版的状态
        if (maskView != null) {
            if (mViewShowState == NARROW_SHOW_STATE) {
                //当前是竖屏
                maskView.showNarrowStateFunView();
            } else if (mViewShowState == MAXIMUM_SHOW_STATE) {
                //当前是横屏
                maskView.showMaximumStateFunView();
            } else if (mViewShowState == FLOATING_SHOW_STATE) {
                //当前是悬浮
                maskView.showFloatingStateFunView();
            }
            maskView.changePlayState(PLAYING_PLAY_STATE);
        }
        //设置播放器当前播放状态
        videoView.setPlayState(PLAYING_PLAY_STATE);
        //延时消失功能蒙版View
        videoView.delayHideMaskView();
        ZVideoPlayMediaManager.obtain().start(mp, videoView);
    }

    /**
     * 暂停播放
     *
     * @param mViewShowState -
     * @param videoView      -
     */
    static void pause(int mViewShowState, ZVideoView videoView) {
        IZBaseVideoFunMaskView maskView = videoView.getMaskView();
        //设置功能蒙版的状态
        if (maskView != null) {
            if (mViewShowState == NARROW_SHOW_STATE) {
                //当前是竖屏
                maskView.showNarrowStateFunView();
            } else if (mViewShowState == MAXIMUM_SHOW_STATE) {
                //当前是横屏
                maskView.showMaximumStateFunView();
            } else if (mViewShowState == FLOATING_SHOW_STATE) {
                //当前是悬浮
                maskView.showFloatingStateFunView();
            }
            maskView.changePlayState(PAUSE_PLAY_STATE);
        }
        //设置播放器当前播放状态
        videoView.setPlayState(PAUSE_PLAY_STATE);
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
        IZBaseVideoFunMaskView maskView = videoView.getMaskView();
        //设置功能蒙版的状态
        if (maskView != null) {
            //不需要判断展示界面是全屏还是悬浮等
            maskView.changePlayState(FINISHED_PLAY_STATE);
            maskView.hideFunView();
        }
        //开始重头播放视频

    }

    /**
     * 播放失败
     *
     * @param mViewShowState -
     * @param videoView      -
     */
    static void playError(int mViewShowState, ZVideoView videoView) {
        IZBaseVideoFunMaskView maskView = videoView.getMaskView();
        //设置功能蒙版的状态
        if (maskView != null) {
            if (mViewShowState == NARROW_SHOW_STATE) {
                //当前是竖屏
                maskView.showNarrowStateFunView();
            } else if (mViewShowState == MAXIMUM_SHOW_STATE) {
                //当前是横屏
                maskView.showMaximumStateFunView();
            } else if (mViewShowState == FLOATING_SHOW_STATE) {
                //当前是悬浮
                maskView.showFloatingStateFunView();
            }
            maskView.changePlayState(ERROR_PLAY_STATE);
        }
        videoView.setPlayState(ERROR_PLAY_STATE);
    }

}
