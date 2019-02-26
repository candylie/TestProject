package com.zk.framework.util.log

import android.util.Log

/**
 * -log工具
 * @author 张科
 * @date 2019/2/25.
 */
object ZLog {
    private val TAG: String = "zkFramework"

    fun e(e: String) {
        Log.e(TAG, e)
    }

    fun d(d: String) {
        Log.d(TAG, d)
    }

    fun i(i: String) {
        Log.i(TAG, i)
    }

    fun v(v: String) {
        Log.v(TAG, v)
    }

}