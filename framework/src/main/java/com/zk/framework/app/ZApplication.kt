package com.zk.framework.app

import android.app.Activity
import android.app.Application

/**
 * -
 * @author 张科
 * @date 2019/2/25.
 */
open class ZApplication : Application() {

    /**
     * 当前栈顶的activity
     */
    open lateinit var nowActivity: Activity

    override fun onCreate() {
        super.onCreate()
    }


}