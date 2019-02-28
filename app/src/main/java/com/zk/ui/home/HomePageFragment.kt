package com.zk.ui.home

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import com.zk.framework.ui.ZBaseFragment
import com.zk.framework.ui.mvp.ZBasePresenter
import com.zk.mytest.R
import com.zk.ui.home.adapter.HomeTaskAdapter
import com.zk.ui.home.bean.HomeTaskBean

/**
 * - 主页界面
 *
 * @author 张科
 * @date 2019/2/23.
 */
open class HomePageFragment : ZBaseFragment() {

    @BindView(R.id.home_list_recycleView)
    open lateinit var mRecycler: RecyclerView

    lateinit var mPresenter: HomePagePresenter
    lateinit var mAdapter: HomeTaskAdapter
    private val mList: ArrayList<HomeTaskBean> = ArrayList()

    override fun getLayoutId(): Int {
        return R.layout.layout_home_fragment
    }

    override fun getFragmentName(): String {
        return "HomePageFragment"
    }

    override fun setPresenter(presenter: ZBasePresenter?) {
        mPresenter = presenter as HomePagePresenter
    }

    override fun initObject() {
        mPresenter = HomePagePresenter(this)
        mAdapter = HomeTaskAdapter(mList, activity)
    }

    override fun initView() {
        mRecycler.layoutManager = LinearLayoutManager(mActivity as Context, LinearLayoutManager.VERTICAL, false)
        mAdapter.bindToRecyclerView(mRecycler)
    }

    override fun initData() {
        for (i in 1..10) run {
            val bean = HomeTaskBean()
            bean.mTaskContent = "12312123123123123123" + i
            bean.mTaskName = "" + i
            mList.add(bean)
        }
        mAdapter.notifyDataSetChanged()
    }

    @OnClick(R.id.home_task1_tv, R.id.home_task2_tv)
    fun onClick(v: View) {
        mPresenter.triggerViewListener(ZBasePresenter.ON_CLICK, v)
    }
}
