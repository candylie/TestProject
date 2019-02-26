package com.zk.ui.home

import com.zk.framework.ui.ZBaseFragment
import com.zk.mytest.R

/**
 * -
 * @author 张科
 * @date 2019/2/25.
 */
class TestFragment : ZBaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.app_test
    }

    /**
     * 获取Fragment的名称
     */
    override fun getFragmentName(): String {
        return "测试界面"
    }

    /**
     * 初始化bundle数据
     */
    override fun initBundle() {
    }

    /**
     * 查找view
     */
    override fun findView() {
    }

    /**
     * 初始化对象
     */
    override fun initObject() {
    }

    /**
     * 初始化view
     */
    override fun initView() {
    }

    /**
     * 初始化数据
     */
    override fun initData() {
    }
}