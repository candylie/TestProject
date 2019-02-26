package com.zk.framework.view.recycleadapter.callback;

import android.view.View;

/**
 * @author Oubowu
 * @date 2016/7/24 23:53
 * <p>顶部标签点击监听</p>
 */
public interface OnHeaderClickListener {

    /**
     * 头部点击回调
     *
     * @param view     - 点击的view
     * @param viewID   - 头部View的ID
     * @param position - 头部所属位置
     */
    void onHeaderClick(View view, int viewID, int position);

    /**
     * 头部长按回调
     *
     * @param view     -点击的view
     * @param viewID   -头部View的ID
     * @param position -头部所属位置
     */
    void onHeaderLongClick(View view, int viewID, int position);


    /**
     * 头部双击回调
     *
     * @param view     -点击的view
     * @param viewID   -头部View的ID
     * @param position -头部所属位置
     */
    void onHeaderDoubleClick(View view, int viewID, int position);

}
