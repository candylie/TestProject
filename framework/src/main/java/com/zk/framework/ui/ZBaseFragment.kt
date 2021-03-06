package com.zk.framework.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.zk.framework.ui.mvp.ZBasePresenter
import com.zk.framework.ui.mvp.ZBaseView
import com.zk.framework.ui.util.status.StatusLayoutManager
import com.zk.framework.util.log.ZLog
import test.zk.com.framwork.R

/**
 * -基础fragment类
 * @author 张科
 * @date 2019/2/23.
 */
abstract class ZBaseFragment : Fragment(), ZBaseView<ZBasePresenter>, UIInitCallBack {
    open var DEBUG: Boolean = true

    private var mFragmentName: String = "--"

    protected lateinit var mActivity: Activity

    private var isFirstVisible: Boolean = true
    private var isFirstInvisible: Boolean = true
    private var isFirstResume: Boolean = true

    private lateinit var rootView: View
    protected lateinit var mStatusLayoutManager: StatusLayoutManager

    private lateinit var mUnbidden: Unbinder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initLayoutManger()
        rootView = mStatusLayoutManager.getRootLayout()
        mUnbidden = ButterKnife.bind(this, rootView)
        mFragmentName = getFragmentName()
        initIntent()
        findView()
        initObject()
        initView()
        initData()
        return rootView
    }

    override fun initIntent() {
    }

    override fun findView() {
    }

    override fun initObject() {
    }

    override fun initView() {
    }

    override fun initData() {
    }

    /**
     * 获取Fragment的名称
     */
    abstract fun getFragmentName(): String

    fun findViewById(@IdRes id: Int): View? {
        return rootView.findViewById(id)
    }

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

    override fun getAttachAcitvity(): Activity {
        return mActivity
    }

    override fun showContentView() {
        mStatusLayoutManager.showContent()
    }

    override fun showErrorLayout() {
        mStatusLayoutManager.showErrorLayout()
    }

    override fun showEmptyLayout() {
        mStatusLayoutManager.showEmptyLayout()
    }

    override fun showLoading() {
        mStatusLayoutManager.showLoading()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as Activity;
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

    override fun onDestroy() {
        super.onDestroy()
        if (DEBUG) {
            ZLog.e("$mFragmentName===onDestroy")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (DEBUG) {
            ZLog.e("$mFragmentName===onDestroyView")
        }
        mUnbidden.unbind()
    }

    override fun onDetach() {
        super.onDetach()
        if (DEBUG) {
            ZLog.e("$mFragmentName===onDestroyView")
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
}