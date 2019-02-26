package com.zk.ui.home

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.zk.framework.ui.ZBaseFragment
import com.zk.mytest.R
import com.zk.ui.home.adapter.HomeTaskAdapter
import com.zk.ui.home.bean.HomeTaskBean

/**
 * -
 *
 * @author 张科
 * @date 2019/2/23.
 */
class HomePageFragment : ZBaseFragment() {

    private lateinit var mRecycler: RecyclerView
    lateinit var mAdapter: HomeTaskAdapter
    private val mList: ArrayList<HomeTaskBean> = ArrayList()

    override fun getLayoutId(): Int {
        return R.layout.home_fragment_layout
    }

    override fun getFragmentName(): String {
        return "HomePageFragment"
    }

    override fun initBundle() {

    }

    override fun findView() {
        mRecycler = findViewById(R.id.home_list_recycleView) as RecyclerView
        mRecycler.layoutManager = LinearLayoutManager(mActivity as Context, LinearLayoutManager.VERTICAL, false)
    }

    override fun initObject() {
        mAdapter = HomeTaskAdapter(mList, activity)
    }

    override fun initView() {
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
}
