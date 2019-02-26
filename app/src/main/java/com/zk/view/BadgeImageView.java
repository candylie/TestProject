package com.zk.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.zk.framework.util.VerifyUtils;
import com.zk.mytest.R;


/**
 * 右上角小红点的ImageView
 * Created by 张科 on 2017/12/1.
 */

public class BadgeImageView extends AppCompatImageView {
    private RectF mRectF;
    private Paint mBgPaint;
    /**
     * 是否显示小红点
     */
    private boolean isShowBadge = true;

    /**
     * 需要显示的小Icon
     */
    private Drawable iconBadgeDrawable;

    /**
     * 半径 px
     */
    private int pointRad;
    /**
     * 最小半径
     */
    private final int MIN_RAD_PX = 2;
    /**
     * 最大半径
     */
    private final int MAX_RAD_PX = 8;

    /**
     * 缩小倍率
     * 小红点是控件宽高中最低值得多少倍
     */
    private int magnification = 5;
    /**
     * xy轴偏移距离
     */
    private float xyOffSet = 0;

    /**
     * 指示颜色
     */
    private int bgColor = Color.RED;


    public BadgeImageView(Context context) {
        super(context);
        init(null, 0);
    }

    public BadgeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BadgeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BadgeImageView);
            magnification = array.getInt(R.styleable.BadgeImageView_badgeMagnification, 5);
            xyOffSet = array.getDimension(R.styleable.BadgeImageView_badgeXYOffSet, 0);
            bgColor = array.getColor(R.styleable.BadgeImageView_badgeColor, Color.RED);
            iconBadgeDrawable = array.getDrawable(R.styleable.BadgeImageView_badgeIcon);
            array.recycle();
        }

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setStyle(Paint.Style.FILL);
        mRectF = new RectF();
        mBgPaint.setColor(bgColor);


    }

    /**
     * 在view展示前设置此属性有效
     *
     * @param isShowBadge true-显示 默认是显示的
     */
    public void isShowBadge(boolean isShowBadge) {
        this.isShowBadge = isShowBadge;
    }

    /**
     * 主动隐藏Badge 用于触发点击事件后主动隐藏小红点
     */
    public void hideBadge(String menuEnum) {
//        if (iconBadgeDrawable == null){
        if (isShowBadge) {
            if (!VerifyUtils.isEmptyStr(menuEnum)) {
                //设置为不是第一次点击
//                HQMenuPFUtil.putBoolean(getContext(), menuEnum, false);
            }
            this.isShowBadge = false;
            invalidate();
        }
//        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isShowBadge) {
            return;
        }
        if (iconBadgeDrawable != null) {
            float viewWidthPx = getMeasuredWidth();
            float viewHeightPx = getMeasuredHeight();
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                Rect rect = iconBadgeDrawable.getBounds();
                rect.set((int) (viewWidthPx - 50), 0, (int) viewWidthPx, 25);
                canvas.drawBitmap(((BitmapDrawable) iconBadgeDrawable).getBitmap(),
                        null, rect,
                        ((BitmapDrawable) iconBadgeDrawable).getPaint());

            } else {
                Rect rect = iconBadgeDrawable.getBounds();
                rect.set((int) (viewWidthPx - 50), 0, (int) viewWidthPx, 25);
                iconBadgeDrawable.draw(canvas);
            }
        } else {
            float viewWidthPx = getMeasuredWidth();
            float viewHeightPx = getMeasuredHeight();
            if (pointRad == 0) {
                float minPx = Math.min(viewHeightPx, viewWidthPx);
                pointRad = (int) (minPx / magnification);
                if (pointRad > MAX_RAD_PX) {
                    pointRad = MAX_RAD_PX;
                } else if (pointRad < MIN_RAD_PX) {
                    pointRad = MIN_RAD_PX;
                }
            }
            mRectF.set(viewWidthPx - 2 * pointRad - xyOffSet,
                    xyOffSet,
                    viewWidthPx - xyOffSet,
                    2 * pointRad + xyOffSet);
            canvas.drawRoundRect(mRectF, pointRad, pointRad, mBgPaint);
        }
    }

    public void setIconBadgeDrawable(Drawable iconBadgeDrawable) {
        this.iconBadgeDrawable = iconBadgeDrawable;
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        super.setBackgroundResource(resId);


    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);

    }

    public void setMagnification(int magnification) {
        this.magnification = magnification;
    }

}
