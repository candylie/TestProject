package videoplay.i;

import android.view.Surface;

import videoplay.ZVideoView;
import videoplay.util.ZVideoPlayControl;


/**
 * -播放器播放相关的指令接口,用于播放器View实现
 *
 * @author 张科
 * @date 2019/3/18.
 */
public interface IZVideoPlayInstruction {


    /**
     * 开始播放
     *
     * @param builder -
     */
    @Deprecated
    void start(ZVideoPlayControl.ZVideoParamBuilder builder);

    /**
     * 开始
     */
    void start();

    /**
     * 暂停
     */
    void pause();

    /**
     * 重播
     */
    void replay();

    /**
     * 转跳指定位置
     */
    void jumpTimestamp(long timestamp);

    /**
     * 获取当前播放状态
     *
     * @return -
     */
    int getPlayState();

    /**
     * 获取用户配置的相关的播放参数
     *
     * @param builder -
     * @return -
     */
    ZVideoView setBuilder(ZVideoPlayControl.ZVideoParamBuilder builder);

    /**
     * 获取用户配置的相关的播放参数
     *
     * @return -
     */
    ZVideoPlayControl.ZVideoParamBuilder getBuilder();

    /**
     * 设置当前的播放状态
     */
    void setPlayState(int playState);

    /**
     * 获取播放器当前播放的位置
     *
     * @return -
     */
    long getNowPlayingTimestamp();

    /**
     * 获取surface
     *
     * @return -
     */
    Surface getSurface();

    /**
     * 延时消失功能蒙版
     */
    void delayHideMaskView();

}
