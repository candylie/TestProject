package com.zk.framework.ui.mvp;

import android.app.Activity;

/**
 * -
 *
 * @author 张科
 * @date 2019/2/28.
 */
public interface ZBaseView<T> {
    /**
     * 获取Context
     *
     * @return -
     */
    Activity getAttachAcitvity();

    /**
     * 设置
     *
     * @param presenter -
     */
    void setPresenter(T presenter);

}
