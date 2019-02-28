package com.zk.ui.home

import android.view.View
import butterknife.OnClick
import com.coder.zzq.smartshow.toast.SmartToast
import com.zk.framework.ui.ZBaseFragment
import com.zk.framework.ui.mvp.ZBasePresenter
import com.zk.mytest.R

/**
 * -测试界面
 *
 * @author 张科
 * @date 2019/2/25.
 */
class TestFragment : ZBaseFragment() {

    override fun setPresenter(presenter: ZBasePresenter?) {
    }

    override fun getLayoutId(): Int {
        return R.layout.app_test
    }

    /**
     * 获取Fragment的名称
     */
    override fun getFragmentName(): String {
        return "测试界面"
    }

    @OnClick(R.id.bt1)
    fun click(v: View) {
        SmartToast.show("ccc")
    }
}