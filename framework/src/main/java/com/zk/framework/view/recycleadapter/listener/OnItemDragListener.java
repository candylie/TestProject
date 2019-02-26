package com.zk.framework.view.recycleadapter.listener;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 *  拖动回调
 *
 * @author luoxw
 * @date 2016/6/20
 */
public interface OnItemDragListener<T> {

    /**
     * 开始拖动
     *
     * @param viewHolder -
     * @param pos        -
     */
    void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos);


    /**
     * 拖动中
     *
     * @param source -
     * @param from   -
     * @param target -
     * @param to     -
     */
    void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to);

    /**
     * 拖动结束
     *
     * @param viewHolder -
     * @param pos        -
     * @param list       -
     */
    void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos, List<T> list);
}
