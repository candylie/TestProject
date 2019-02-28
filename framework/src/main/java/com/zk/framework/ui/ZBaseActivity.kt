package com.zk.framework.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import butterknife.ButterKnife
import com.zk.framework.ui.util.status.StatusLayoutManager
import com.zk.framework.util.log.ZLog
import test.zk.com.framwork.R

/**
 * -基础activity
 * @author 张科
 * @date 2019/2/25.
 */
abstract class ZBaseActivity : AppCompatActivity(), UIInitCallBack {
    val DEBUG: Boolean = true

    private var mActivityName: String = ""
    private lateinit var statusLayoutManager: StatusLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusLayoutManager = StatusLayoutManager.Builder(this)
                .setContentLayout(getLayoutId())
                .setEmptyLayout(R.layout.layout_status_layout_manager_empty)
                .setErrorLayout(R.layout.layout_status_layout_manager_error)
                .setLoadingLayout(R.layout.layout_status_layout_manager_loading)
                .setEmptyLayoutClickId(R.id.bt_status_empty_click)
                .setErrorLayoutClickId(R.id.tv_status_error_content)
                .newBuilder()
        setContentView(statusLayoutManager.getRootLayout())
        statusLayoutManager.showContent()
        ButterKnife.bind(this)
        mActivityName = getActivityName()
        setStatusBar()
        initIntent()
        findView()
        initObject()
        initView()
        initData()
    }

    /**
     * 获取界面名称
     */
    abstract fun getActivityName(): String


    /**
     * 设置透明状态栏
     * @param isDarkColor Boolean ：状态栏文字颜色是否为暗色
     */
    fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            //根据上面设置是否对状态栏单独设置颜色
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            val localLayoutParams = window.attributes
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags)
        }
    }

    override fun showContentView() {
        statusLayoutManager.showContent()
    }

    override fun showErrorLayout() {
        statusLayoutManager.showErrorLayout()
    }

    override fun showEmptyLayout() {
        statusLayoutManager.showEmptyLayout()
    }

    override fun showLoading() {
        statusLayoutManager.showLoading()
    }

    override fun onStart() {
        super.onStart()
        if (DEBUG) {
            ZLog.e("$mActivityName===onStart")
        }
    }

    override fun onResume() {
        super.onResume()
        if (DEBUG) {
            ZLog.e("$mActivityName===onResume")
        }
    }

    override fun onPause() {
        super.onPause()
        if (DEBUG) {
            ZLog.e("$mActivityName===onPause")
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        if (DEBUG) {
            ZLog.e("$mActivityName===onSaveInstanceState")
        }
    }

    override fun onStop() {
        super.onStop()
        if (DEBUG) {
            ZLog.e("$mActivityName===onStop")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (DEBUG) {
            ZLog.e("$mActivityName===onDestroy")
        }
    }

}