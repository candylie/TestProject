package com.zk.framework.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zk.framework.ui.util.status.StatusLayoutManager
import com.zk.framework.util.log.ZLog
import test.zk.com.framwork.R

/**
 * -基础fragment类
 * @author 张科
 * @date 2019/2/23.
 */
abstract class ZBaseFragment : Fragment() {
    open var DEBUG: Boolean = true

    private lateinit var mFragmentName: String

    private var isFirstVisible: Boolean = true
    private var isFirstInvisible: Boolean = true
    private var isFirstResume: Boolean = true

    private lateinit var rootView: View
    protected lateinit var mStatusLayoutManager: StatusLayoutManager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initLayoutManger()
        rootView = mStatusLayoutManager.getRootLayout()
        mFragmentName = getFragmentName()
        initBundle()
        findView()
        initObject()
        initView()
        initData()
        return rootView
    }

    abstract fun getLayoutId(): Int

    /**
     * 获取Fragment的名称
     */
    abstract fun getFragmentName(): String

    /**
     * 初始化Layout状态管理
     */
    fun initLayoutManger() {
        mStatusLayoutManager = StatusLayoutManager.Builder(activity as Context)
                .setContentLayout(getLayoutId())
                .setEmptyLayout(R.layout.layout_status_layout_manager_empty)
                .setErrorLayout(R.layout.layout_status_layout_manager_error)
                .setLoadingLayout(R.layout.layout_status_layout_manager_loading)
                .setEmptyLayoutClickId(R.id.bt_status_empty_click)
                .setErrorLayoutClickId(R.id.tv_status_error_content)
                .newBuilder()
        mStatusLayoutManager.showContent()
    }


    override fun onResume() {
        super.onResume()
        if (isFirstResume) {
            isFirstResume = false
            return
        }
        if (userVisibleHint) {
            onUserVisible()
        }
    }

    override fun onPause() {
        super.onPause()
        if (userVisibleHint) {
            onUserInvisible()
        }
    }

    /**
     * 首次打开列表界面, 所有被初始化的界面都会调用此方法,
     * 并且会在onCreateView 和onResume之前执行
     * 并且isVisibleToUser为false
     * 当所有的界面的此方法设置为false后, 默认展示的界面会重新调用此方法,并设置为true
     * 接着执行当前展示界面的onCreateView,onResume
     * <p>
     * 锁屏时不会回调此方法, 解锁时也不会回调此方法,不过解锁会回调onResume方法
     * <p>
     * 点击到二级界面和从二级界面返回到此界面时,生命周期同锁屏一致,都不会执行此方法
     *
     * @param isVisibleToUser -
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {//用户可见
            if (isFirstVisible) {
                isFirstVisible = false
                onFirstUserVisible()
            } else {
                onUserVisible()
            }
        } else {//用户不可见
            if (isFirstInvisible) {
                isFirstInvisible = false
                onFirstUserInvisible()
            } else {
                onUserInvisible()
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }


    /**
     * 用户第一次可见
     */
    open fun onFirstUserVisible() {
        if (DEBUG) {
            ZLog.e("$mFragmentName===onFirstUserVisible")
        }

    }

    /**
     * 用户第一次不可见
     */
    open fun onFirstUserInvisible() {
        if (DEBUG) {
            ZLog.e("$mFragmentName===onFirstUserInvisible")
        }
    }

    /**
     * 用户不可见
     */
    open fun onUserInvisible() {
        if (DEBUG) {
            ZLog.e("$mFragmentName===onUserInvisible")
        }
    }

    /**
     * 用户可见时调用
     */
    open fun onUserVisible() {
        if (DEBUG) {
            ZLog.e("$mFragmentName===onUserVisible")
        }
    }


    /**
     * 初始化bundle数据
     */
    abstract fun initBundle()

    /**
     * 查找view
     */
    abstract fun findView()

    /**
     * 初始化对象
     */
    abstract fun initObject()

    /**
     * 初始化view
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()


}