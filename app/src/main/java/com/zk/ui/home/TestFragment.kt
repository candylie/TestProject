package com.zk.ui.home

import android.content.Intent
import android.view.View
import butterknife.OnClick
import com.coder.zzq.smartshow.toast.SmartToast
import com.zk.framework.ui.ZBaseFragment
import com.zk.framework.ui.mvp.ZBasePresenter
import com.zk.mytest.R
import com.zk.ui.main.CommonActivity
import com.zk.ui.videotest.VideoTextFragment

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

    @OnClick(R.id.bt1, R.id.bt2)
    fun click(v: View) {
        when (v.id) {
            R.id.bt1 -> SmartToast.show("ccc")
            R.id.bt2 -> {
                val a = Intent(activity, CommonActivity::class.java)
                a.putExtra(CommonActivity.FRAGMENT_PATH, VideoTextFragment::class.java.name)
                activity!!.startActivity(a)
            }
        }

    }
}