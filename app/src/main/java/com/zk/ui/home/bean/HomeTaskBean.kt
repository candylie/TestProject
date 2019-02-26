package com.zk.ui.home.bean

import com.zk.framework.view.recycleadapter.entity.MultiItemEntity
import com.zk.ui.base.bean.BaseBean

/**
 * -
 * @author 张科
 * @date 2019/2/26.
 */

class HomeTaskBean : BaseBean(), MultiItemEntity {
    override fun getItemType(): Int {
        return 1
    }

    var mTaskType: Int = -1

    var mTaskName: String = ""

    var mTaskContent: String = ""

}