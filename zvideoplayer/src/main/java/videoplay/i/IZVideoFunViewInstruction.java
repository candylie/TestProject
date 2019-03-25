package videoplay.i;

/**
 * -播放器功能按钮的界面相关指令接口
 *
 * @author 张科
 * @date 2019/3/18.
 */
public interface IZVideoFunViewInstruction {

    /**
     * 竖屏的时候展示相关的功能按钮的方法
     */
    void showNarrowStateFunView();

    /**
     * 横屏的时候展示相关的功能按钮的方法
     */
    void showMaximumStateFunView();

    /**
     * 悬浮的时候展示相关的功能按钮的方法
     */
    void showFloatingStateFunView();

    /**
     * 隐藏功能蒙版,展示底部的细进度条
     */
    void hideFunView();

}
