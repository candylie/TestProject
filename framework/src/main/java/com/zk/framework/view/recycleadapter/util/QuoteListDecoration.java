//package com.zk.framework.view.recycle.util;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.RecyclerView.LayoutManager;
//import android.support.v7.widget.RecyclerView.State;
//import android.support.v7.widget.StaggeredGridLayoutManager;
//import android.view.View;
//
//
//import java.util.HashMap;
//
//import test.zk.com.framwork.R;
//
///**
// * 用在 recycleView的分割线，可进行自定义处理
// *
// * @author 张科
// * @date 2018/07/25
// */
//public class QuoteListDecoration extends RecyclerView.ItemDecoration implements SkinningInterface {
//
//    @SuppressLint("UseSparseArrays")
//    private HashMap<Integer, Decoration> decorations = new HashMap<>();
//
//    private int mDividerPx = 1;
//
//
//    private int leftMargin;
//    private int topBottomMargin;
//
//    private Paint paint;
//
//    public QuoteListDecoration(Context context) {
//        mDividerPx = leftMargin = (int) (context.getResources().getDimension(R.dimen.tk_hq_split_lint_height) + 0.5);
//        topBottomMargin = leftMargin = (int) context.getResources().getDimension(R.dimen.def_margin);
//        paint = new Paint();
//        paint.setColor(SkinCompatResources.getInstance().getColor(R.color.tk_hq_divide_line));
//    }
//
//
//    @Override
//    public void onDraw(Canvas c, RecyclerView parent, State state) {
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            final View child = parent.getChildAt(i);
//            //获取设置的tag 由于childCount是当前屏幕所展示的count,然而decorations中存贮的是所有position的,
//            //所以需要用tag把child的真实位置存储下来
//            int position = NumberUtils.parseInt(child.getTag().toString());
//            Decoration rectF = decorations.get(position);
//
//            if (rectF == null) {
//                continue;
//            }
//            drawHorizontal(c, child, rectF);
//            drawVertical(c, child, rectF);
//        }
//    }
//
//
//    private void drawHorizontal(Canvas c, View child, Decoration rectF) {
//        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//
//        final int left = child.getLeft() - params.leftMargin + rectF.marginLeft;
//        final int top = child.getBottom() + rectF.marginTop;
//        final int right = child.getRight() + params.rightMargin - rectF.marginRight;
//        final int bottom = top + rectF.marginBottom;
//        if (rectF.isNeedBottomLine) {
//            c.drawRect(left, top, right, bottom, paint);
//        }
//    }
//
//    private void drawVertical(Canvas c, View child, Decoration rectF) {
//        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//
//        final int left = child.getRight();
//        final int top = child.getTop() + params.topMargin + rectF.marginTop;
//        final int right = left + mDividerPx;
//        final int bottom = child.getBottom() - params.bottomMargin - rectF.marginBottom;
//        c.drawRect(left, top, right, bottom, paint);
//    }
//
//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
//        RecyclerView.Adapter adapter = parent.getAdapter();
//        int childAdapterPosition = parent.getChildAdapterPosition(view);
//        //这里很重要
//        view.setTag(childAdapterPosition);
//        Decoration decoration;
//        if (adapter.getItemViewType(childAdapterPosition) == QuoteListAdapter.STICKINESS_DATA_TYPE_HEAD_DEF) {
//            decoration = new Decoration();
//            decoration.marginLeft = 0;
//            decoration.marginRight = 0;
//            decoration.marginTop = 0;
//            decoration.marginBottom = mDividerPx;
//            decorations.put(childAdapterPosition, decoration);
//            //左右不需要制造间隔 所以传0
//            outRect.set(0, 0, 0, mDividerPx);
//        } else if (adapter.getItemViewType(childAdapterPosition) == QuoteListAdapter.STICKINESS_DATA_TYPE_HEAD_RG) {
//            //不需要划线
//            decoration = new Decoration();
//            decoration.marginLeft = 0;
//            decoration.marginRight = 0;
//            decoration.marginTop = 0;
//            decoration.marginBottom = 0;
//            decorations.put(childAdapterPosition, decoration);
//            outRect.set(0, 0, 0, 0);
//        } else if (adapter.getItemViewType(childAdapterPosition) == QuoteListAdapter.STICKINESS_DATA_TYPE_LIST) {
//            decoration = new Decoration();
//            decoration.marginLeft = leftMargin;
//            decoration.marginRight = 0;
//            decoration.marginTop = 0;
//            decoration.marginBottom = mDividerPx;
//            decorations.put(childAdapterPosition, decoration);
//            //左右不需要制造间隔 所以传0
//            outRect.set(0, 0, 0, mDividerPx);
//        } else if (adapter.getItemViewType(childAdapterPosition) == QuoteListAdapter.STICKINESS_DATA_TYPE_GRID ||
//                adapter.getItemViewType(childAdapterPosition) == QuoteListAdapter.STICKINESS_DATA_TYPE_FUNCTION_GRID) {
//            decoration = new Decoration();
//            decoration.marginLeft = 0;
//            decoration.marginRight = mDividerPx;
//            decoration.marginTop = topBottomMargin;
//            decoration.marginBottom = topBottomMargin;
//            decoration.isNeedBottomLine = false;
//            decorations.put(childAdapterPosition, decoration);
//            outRect.set(0, 0, mDividerPx, mDividerPx);
//        }
//    }
//
//    @Override
//    public void updateSkin() {
//        paint.setColor(SkinCompatResources.getInstance().getColor(R.color.tk_hq_divide_line));
//    }
//
//    @Override
//    public View getView() {
//        return null;
//    }
//
//    @Override
//    public void setSkinning(Resources.Theme theme) {
//        paint.setColor(SkinCompatResources.getInstance().getColor(R.color.tk_hq_divide_line));
//    }
//
//    private class Decoration {
//        /**
//         * 是否需要画横线
//         */
//        boolean isNeedBottomLine = true;
//        int marginLeft, marginRight, marginTop, marginBottom;
//    }
//
//
//    /**
//     * 获取列数据
//     *
//     * @param parent -
//     * @return -
//     */
//    private int getSpanCount(RecyclerView parent) {
//        // 列数
//        int spanCount = -1;
//        LayoutManager layoutManager = parent.getLayoutManager();
//        if (layoutManager instanceof GridLayoutManager) {
//            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
//        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
//            spanCount = ((StaggeredGridLayoutManager) layoutManager)
//                    .getSpanCount();
//        }
//        return spanCount;
//    }
//
//}