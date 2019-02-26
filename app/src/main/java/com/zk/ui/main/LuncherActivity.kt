package com.zk.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Message
import com.zk.framework.ui.ZBaseActivity
import com.zk.mytest.R

/**
 * -
 * @author 张科
 * @date 2019/2/26.
 */
class LuncherActivity : ZBaseActivity() {

    private val mHandler: MyHandler = MyHandler()

    override fun getLayoutId(): Int {
        return R.layout.layout_luncher
    }

    override fun getActivityName(): String {
        return "启动页"
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
        val msg = Message()
        msg.what = 1
        msg.obj = this
        mHandler.sendMessageDelayed(msg, 900)
    }

    class MyHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            val intent = Intent((msg?.obj as Activity), MainActivity::class.java)
            (msg?.obj as Activity).startActivity(intent)
        }
    }

}