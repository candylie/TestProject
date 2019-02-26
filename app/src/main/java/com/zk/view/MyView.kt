package com.zk.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView

/**
 * -
 * @author 张科
 * @date 2018/9/14.
 */
class MyView : TextView {
    private val TAG: String = "ZK_MyView"
    lateinit var xpaint: Paint
    lateinit var ypaint: Paint


    constructor(context: Context?) : super(context) {
        initPaint()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initPaint()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initPaint()
    }


    fun initPaint() {
        xpaint = Paint()
        ypaint = Paint()
        xpaint.strokeWidth = 2f
        ypaint.strokeWidth = 2f
        xpaint.color = Color.RED
        xpaint.color = Color.GREEN
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "dispatchTouchEvent==$event")
        val b = super.dispatchTouchEvent(event)
        return b
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawLine(0f, 0f, width.toFloat(), 2f, xpaint)
        canvas?.drawLine(0f, 0f, 2f, height.toFloat(), xpaint)
        canvas?.translate(50f, 50f)
        canvas?.drawLine(0f, 0f, width.toFloat(), 2f, xpaint)
        canvas?.drawLine(0f, 0f, 2f, height.toFloat(), xpaint)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "onTouchEvent==$event")
        var b = super.onTouchEvent(event)
        var downx = 0f;
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                downx = event.x
                b = true
            }
            MotionEvent.ACTION_MOVE -> {
                if (Math.abs(event.x - downx) > 200) {
                    //如果超出200的限制就拦截事件
                    parent.requestDisallowInterceptTouchEvent(false)
                    b = false
                } else {
                    parent.requestDisallowInterceptTouchEvent(true)
                    b = true
                }
            }
            MotionEvent.ACTION_UP -> {
                b = true
            }
        }

        return b
    }


}