package com.zk.framework.view.videoplay.constant;

/**
 * -播放器一些状态常量
 *
 * @author 张科
 * @date 2019/3/19.
 */
public class ZVideoConstant {
    //状态定义start
    /**
     * 缩小状态 如竖屏展示的时候
     */
    public static final int NARROW_SHOW_STATE = 1;
    /**
     * 最大状态 如全屏展示的时候
     */
    public static final int MAXIMUM_SHOW_STATE = 2;
    /**
     * 悬浮状态 如界面滚动的时候的悬浮状态
     */
    public static final int FLOATING_SHOW_STATE = 3;

    /**
     * 未初始化状态
     */
    public static final int UNINIT_PLAY_STATE = 1000;
    /**
     * 空闲中
     * Idle 状态：当使用new()方法创建一个MediaPlayer对象或者调用了其reset()方法时，该MediaPlayer对象处于idle状态。
     */
    public static final int IDLE_PLAY_STATE = 1001;
    /**
     * 准备中的状态
     */
    public static final int PREPARING_PLAY_STATE = 1002;
    /**
     * 准备完成的状态的状态
     */
    public static final int PREPARED_PLAY_STATE = 1003;
    /**
     * 播放中状态
     */
    public static final int PLAYING_PLAY_STATE = 1004;

    /**
     * 暂停状态
     */
    public static final int PAUSE_PLAY_STATE = 1005;

    /**
     * 播放完毕状态
     */
    public static final int FINISHED_PLAY_STATE = 1006;

    /**
     * 缓冲中的状态
     */
    public static final int LOADING_PLAY_STATE = 1007;
    /**
     * 加载失败的状态
     */
    public static final int ERROR_PLAY_STATE = 1008;
    //状态定义end

    /**
     * 隐藏功能蒙版的消息号
     */
    public final static int HIDE_MASK_VIEW_MESSAGE_ACTION = 1;
    /**
     * 显示功能蒙版的消息号
     */
    public final static int SHOW_MASK_VIEW_MESSAGE_ACTION = 2;
    /**
     * 改变当前播放状态
     */
    public final static int CHANGE_PLAY_STATE_MESSAGE_ACTION = 3;
    /**
     * 播放view初始化完成, 通知播放器开始初始化 的消息号
     */
    public final static int MEDIA_PLAY_PREPARING_MESSAGE_ACTION = 4;
    /**
     * 播放view初始化完成, 通知播放器开始初始化 的消息号
     */
    public final static int MEDIA_PLAY_PREPARED_MESSAGE_ACTION = 5;
    /**
     * 播放播放完成, 通知播放器开始初始化 的消息号
     */
    public final static int PLAY_FINISH_MESSAGE_ACTION = 6;
    /**
     * 播放播放出错, 通知播放器开始初始化 的消息号
     */
    public final static int PLAY_ERROR_MESSAGE_ACTION = 7;

}
