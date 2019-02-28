package com.zk.ui.main

import android.app.Service
import android.graphics.PixelFormat
import android.os.Build
import android.support.design.widget.FloatingActionButton
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import com.zk.framework.ui.ZBaseActivity
import com.zk.framework.ui.ZBaseFragment
import com.zk.mytest.R
import com.zk.ui.home.HomePageFragment
import com.zk.ui.home.TestFragment
import com.zk.view.bottombar.BottomBarItem
import com.zk.view.bottombar.BottomBarLayout


class MainActivity : ZBaseActivity(), BottomBarLayout.OnItemSelectedListener {


    private lateinit var mFatButton: FloatingActionButton

    private lateinit var mBottomBar: BottomBarLayout


    private lateinit var mFragments: ArrayList<ZBaseFragment>

    private val mNormalIconIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher)

    private val mSelectedIconIds = intArrayOf(R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round)

    private val mTitleIds = intArrayOf(R.string.tab_home, R.string.tab_test)

    override fun getLayoutId(): Int {
        return R.layout.app_main
    }

    /**
     * 获取界面名称
     */
    override fun getActivityName(): String {
        return "MainActivity"
    }

    /**
     * 初始化intent
     */
    override fun initIntent() {


    }

    /**
     * 查找View
     */
    override fun findView() {
        mBottomBar = findViewById(R.id.app_main_content_bbl)
    }

    /**
     * 初始化对象
     */
    override fun initObject() {
        mFragments = ArrayList()
        val pageFragment = HomePageFragment()
        val testFragment = TestFragment()
        mFragments.add(pageFragment)
        mFragments.add(testFragment)

        mFragments.forEachIndexed { index, zBaseFragment ->
            val item = createBottomBarItem(index)
            mBottomBar.addItem(item)
        }
    }

    /**
     * 初始化view
     */
    override fun initView() {
        val windowManager = getSystemService(Service.WINDOW_SERVICE) as WindowManager
        val ma = getWindowManager()

        val bt = Button(this)
        bt.text = "BT"
        bt.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val layoutParams = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams(300,
                    100,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSPARENT)
        } else {
            WindowManager.LayoutParams(300,
                    100,
                    WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSPARENT)
        }
        layoutParams.gravity = Gravity.CENTER
        windowManager.addView(bt, layoutParams)


        val mFragmentTransaction = supportFragmentManager.beginTransaction()
        mFragments.forEach { f ->
            if (!f.isAdded) {
                mFragmentTransaction.add(R.id.app_main_content_fl, f, f.getFragmentName())
                        .hide(f)
            }
        }
        mFragmentTransaction.show(mFragments[0])
                .commit()

        mBottomBar.setOnItemSelectedListener(this)
    }

    /**
     * 初始化数据
     */
    override fun initData() {
    }

    private fun createBottomBarItem(i: Int): BottomBarItem {
        return BottomBarItem.Builder(this)
                .titleTextSize(8)
                .titleNormalColor(R.color.color_666)
                .titleSelectedColor(R.color.color_333)
                //              .openTouchBg(false)
                //              .marginTop(5)
                //              .itemPadding(5)
                //              .unreadNumThreshold(99)
                //              .unreadTextColor(R.color.white)

                //还有很多属性，详情请查看Builder里面的方法
                //There are still many properties, please see the methods in the Builder for details.
                .create(mNormalIconIds[i], mSelectedIconIds[i], getString(mTitleIds[i]))
    }

    override fun onItemSelected(bottomBarItem: BottomBarItem?, previousPosition: Int, currentPosition: Int) {
        val mFragmentTransaction = supportFragmentManager.beginTransaction()
        mFragmentTransaction.hide(mFragments[previousPosition])
                .show(mFragments[currentPosition])
                .commit()
    }

    override fun onBackPressed() {

    }
}
