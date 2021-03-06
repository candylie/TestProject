package com.zk.framework.ui.util.status

import android.view.View

/**
 * @Author : zhangke
 * @Date ：2018/9/25 - 16:16
 * @Email：878749089@qq.com
 * @descriptio：状态布局点击监听接口
 */
interface OnStatusLayoutClickListener {

    /**
     * 空布局点击
     * @param view View
     */
    fun onEmptyViewClick(view: View)

    /**
     * 错误布局点击
     * @param view View
     */
    fun onErrorViewClick(view: View)
}