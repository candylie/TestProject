package com.zk.framework.view.videoplay.mask;

import android.view.View;

import com.zk.framework.view.videoplay.i.IZVideoPlayInstruction;

/**
 * -功能蒙版接口
 *
 * @author 张科
 * @date 2019/3/19.
 */
public interface IZBaseVideoFunMaskView {
    /**
     * 隐藏功能蒙版,展示底部的细进度条
     *
     * @param mViewShowState -界面展示状态
     *                       未展开的缩小状态(默认状态)
     *                       全屏展开状态(横屏全屏/竖屏全屏)
     *                       悬浮状态
     */
    void showBarView(int mViewShowState);

    /**
     * 隐藏功能蒙版,展示底部的细进度条
     */
    void hideBarView();

    /**
     * 展示中间的操作按钮
     */
    void showStateChangeButton();

    /**
     * 隐藏中间的操作按钮
     */
    void hideStateChangeButton();

    /**
     * 设置返回键回调
     *
     * @param clickCallBack -
     */
    void setBackImgClickCallBack(View.OnClickListener clickCallBack);

    /**
     * 设置右下角切换播放器全屏状态的回调
     *
     * @param clickCallBack -
     */
    void setChangeWindowClickCallBack(View.OnClickListener clickCallBack);

    /**
     * 设置播放器播放指令回调
     *
     * @param playInstruction -
     */
    void setPlayInstruction(IZVideoPlayInstruction playInstruction);


    /**
     * 切换播放状态
     *
     * @param nextPlayState 接下来播放器的播放状态
     */
    void changePlayState(int nextPlayState);


}
