package com.zk.ui.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zk.framework.view.recycleadapter.BaseMultiItemQuickAdapter;
import com.zk.mytest.R;
import com.zk.ui.home.bean.HomeTaskBean;

import java.util.List;

/**
 * -
 *
 * @author 张科
 * @date 2019/2/26.
 */
public class HomeTaskAdapter extends BaseMultiItemQuickAdapter<HomeTaskBean, HomeTaskHolder> {

    private Context mContext;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data    A new mList is created out of this one to avoid mutable mList
     * @param context -
     */
    public HomeTaskAdapter(List data, Context context) {
        super(data, context);
        mContext = context;
    }

    @SuppressLint("InflateParams")
    @Override
    protected HomeTaskHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_task_recycleview, null, false);
        return new HomeTaskHolder(view);
    }

    @Override
    protected void convert(HomeTaskHolder helper, HomeTaskBean item) {
        helper.setTaskName(item.getMTaskName()).setTaskContent(item.getMTaskContent());
    }
}
