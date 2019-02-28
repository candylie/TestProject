package com.zk.framework.ui.mvp;

import android.view.View;

/**
 * -
 *
 * @author 张科
 * @date 2019/2/28.
 */
public interface ZBasePresenter {

    /**
     * 点击事件
     */
    int ON_CLICK = 0x001;


    /**
     * radioButton点击事件
     */
    int ON_CHECKED_CHANGE = 0x002;


    /**
     * 长按事件
     */
    int ON_LONG_CLICK = 0x003;


    /**
     * 每条点击事件
     */
    int ON_ITEM_CLICK = 0x004;

    /**
     * 每条触摸事件
     */
    int ON_ITEM_TOUCH = 0x005;


    /**
     * 触摸事件
     */
    int ON_TOUCH = 0x006;


    /**
     * 每条长按事件
     */
    int ON_ITEM_LONG_TOUCH = 0x007;


    /**
     * 每条长按事件
     */
    int ON_PULL_TO_REFRESH = 0x008;


    /**
     * 注册监听
     *
     * @param flag 类型
     * @param view 控件
     */
    void registerListener(int flag, View view);


    /**
     * 如果用ButterKnife就直接调用此方法
     *
     * @param flag -类型
     * @param view -控件
     */
    void triggerViewListener(int flag, View view);
}
