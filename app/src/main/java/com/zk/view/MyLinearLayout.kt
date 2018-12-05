package com.zk.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout

/**
 * -
 * @author 张科
 * @date 2018/9/14.
 */
class MyLinearLayout : LinearLayout {

    private val TAG: String = "ZK_MyLinearLayout"

    constructor(context: Context?) : super(context) {
        val mContext = context
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val mContext = context
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val mContext = context
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e(TAG, "onTouchEvent==" + event)
        var b = super.onTouchEvent(event)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                b = true
            }
            MotionEvent.ACTION_MOVE -> {
                b = true
            }
        }
        return b
    }


    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        Log.e(TAG, "requestDisallowInterceptTouchEvent==" + disallowIntercept)
        super.requestDisallowInterceptTouchEvent(disallowIntercept)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.e(TAG, "dispatchTouchEvent==$ev")
        val b = super.dispatchTouchEvent(ev);
        return b
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.e(TAG, "onInterceptTouchEvent==$ev")
        if (500 > ev?.x!!) {
            return true
        } else {
            return super.onInterceptTouchEvent(ev)
        }
    }
}