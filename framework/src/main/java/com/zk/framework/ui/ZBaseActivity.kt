package com.zk.framework.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.zk.framework.util.log.ZLog

/**
 * -基础activity
 * @author 张科
 * @date 2019/2/25.
 */
abstract class ZBaseActivity : AppCompatActivity() {
    val DEBUG: Boolean = true

    private var mActivityName: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = LayoutInflater.from(this).inflate(getLayoutId(), null)
        setContentView(rootView)
        mActivityName = getActivityName()
        setStatusBar()
        initIntent()
        findView()
        initObject()
        initView()
        initData()
    }

    abstract fun getLayoutId(): Int

    /**
     * 获取界面名称
     */
    abstract fun getActivityName(): String

    /**
     * 初始化intent
     */
    abstract fun initIntent()

    /**
     * 查找View
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