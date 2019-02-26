package com.zk.ui.home.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zk.framework.view.recycleadapter.BaseViewHolder;
import com.zk.mytest.R;

/**
 * -
 *
 * @author 张科
 * @date 2019/2/26.
 */
public class HomeTaskHolder extends BaseViewHolder {
    private ImageView imageView;
    private TextView taskName;
    private TextView taskContent;


    HomeTaskHolder(View view) {
        super(view);
        imageView = view.findViewById(R.id.item_task_icon_iv);
        taskName = view.findViewById(R.id.item_task_name_tv);
        taskContent = view.findViewById(R.id.item_task_content_tv);
    }

    public HomeTaskHolder setTaskName(String name) {
        taskName.setText(name);
        return this;
    }

    public HomeTaskHolder setTaskContent(String content) {
        taskContent.setText(content);
        return this;
    }

}
