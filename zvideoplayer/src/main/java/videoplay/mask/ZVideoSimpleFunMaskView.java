package videoplay.mask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import test.zk.com.framwork.R;
import videoplay.i.IZVideoPlayInstruction;

import static videoplay.constant.ZVideoConstant.*;

/**
 * -简单的默认功能蒙版View
 *
 * @author 张科
 * @date 2019/3/19.
 */
public class ZVideoSimpleFunMaskView extends FrameLayout implements IZBaseVideoFunMaskView,
        View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    //--功能蒙版相关-start
    /**
     * 功能蒙版View
     */
    private View mTopBarView;
    private View mButtomBarView;
    private ImageView mThumbImg;

    private View mBackImg;

    private TextView mTitleTv;

    private TextView mBatteryTv;

    private TextView mTimeTv;

    private ImageView mPlayStateImg;
    private TextView mPlayTipTv;

    private TextView mPlayingTimeTv;
    private TextView mAllTimeTv;
    private SeekBar mPlaySeekBar;
    private ImageView mChangeWindowImg;

    private LayoutInflater mInflater;
    //--功能蒙版相关-end

    /**
     * 返回按钮点击事件
     */
    private OnClickListener mBackImgClickCallBack;
    private OnClickListener mChangeWindowClickCallBack;
    private IZVideoPlayInstruction mPlayInstruction;

    private Context mContext;

    private boolean isShowingBarView = true;
    private boolean isShowingStateView = true;

    public ZVideoSimpleFunMaskView(@NonNull Context context) {
        this(context, null);
    }

    public ZVideoSimpleFunMaskView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZVideoSimpleFunMaskView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        mInflater = LayoutInflater.from(context);
        View mFunView = mInflater.inflate(R.layout.layout_videoview_simple_function_mask, null, false);
        addView(mFunView);
        //缩略图
        mThumbImg = mFunView.findViewById(R.id.videoView_functionMask_thumb_iv);
        //顶部功能按钮
        mTopBarView = mFunView.findViewById(R.id.videoView_functionMask_top_bar);
        mBackImg = mFunView.findViewById(R.id.videoView_functionMask_back_iv);
        mTitleTv = mFunView.findViewById(R.id.videoView_functionMask_title_tv);
        mBatteryTv = mFunView.findViewById(R.id.videoView_functionMask_battery_tv);
        mTimeTv = mFunView.findViewById(R.id.videoView_functionMask_time_tv);
        //中间的播放状态按钮
        mPlayStateImg = mFunView.findViewById(R.id.videoView_functionMask_playState_img);
        mPlayTipTv = mFunView.findViewById(R.id.videoView_functionMask_replay_tv);
        //底部功能按钮
        mButtomBarView = mFunView.findViewById(R.id.videoView_functionMask_bottom_bar);
        mPlayingTimeTv = mFunView.findViewById(R.id.videoView_functionMask_playing_time_tv);
        mAllTimeTv = mFunView.findViewById(R.id.videoView_functionMask_all_time_tv);
        mPlaySeekBar = mFunView.findViewById(R.id.videoView_functionMask_seekBar);
        mChangeWindowImg = mFunView.findViewById(R.id.videoView_functionMask_changeWindow_img);
        //设置监听
        mBackImg.setOnClickListener(this);
        mPlayStateImg.setOnClickListener(this);
        mChangeWindowImg.setOnClickListener(this);
        mPlaySeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void showBarView(int mViewShowState) {
        isShowingBarView = true;
        if (mViewShowState == NARROW_SHOW_STATE) {
            //当前是竖屏
            showNarrowStateFunView();
        } else if (mViewShowState == MAXIMUM_SHOW_STATE) {
            //当前是横屏
            showMaximumStateFunView();
        } else if (mViewShowState == FLOATING_SHOW_STATE) {
            //当前是悬浮
            showFloatingStateFunView();
        }
    }

    @Override
    public void hideBarView() {
        isShowingBarView = false;
        mTopBarView.setVisibility(GONE);
        mButtomBarView.setVisibility(GONE);
    }

    @Override
    public void showStateChangeButton() {
        isShowingStateView = true;
        mPlayStateImg.setVisibility(VISIBLE);
        mPlayTipTv.setVisibility(VISIBLE);
    }

    @Override
    public void hideStateChangeButton() {
        isShowingStateView = false;
        mPlayStateImg.setVisibility(GONE);
        mPlayTipTv.setVisibility(GONE);
    }

    @Override
    public void changePlayState(int nextPlayState) {
        switch (nextPlayState) {
            case PLAYING_PLAY_STATE:
                mPlayStateImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_video_play_selector));
                break;
            case PAUSE_PLAY_STATE:
                mPlayStateImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_video_pause_selector));
                break;
            case FINISHED_PLAY_STATE:
                mPlayStateImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_video_replay_selector));
                mPlayTipTv.setText(R.string.z_framework_video_replay);
                break;
            case LOADING_PLAY_STATE:
                mPlayStateImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_video_loading_normal));
                mPlayTipTv.setText(R.string.z_framework_video_loading);
                break;
            case ERROR_PLAY_STATE:
                mPlayStateImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_video_replay_selector));
                mPlayTipTv.setText(R.string.z_framework_video_reloading);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isShowingBarView() {
        return isShowingBarView;
    }

    @Override
    public boolean isShowingStateView() {
        return isShowingStateView;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.videoView_functionMask_back_iv) {
            if (mBackImgClickCallBack != null) {
                mBackImgClickCallBack.onClick(v);
            }
        } else if (v.getId() == R.id.videoView_functionMask_playState_img) {
            if (mPlayInstruction != null) {
                int mViewPlayState = mPlayInstruction.getPlayState();
                if (mViewPlayState == FINISHED_PLAY_STATE) {
                    mPlayInstruction.replay();
                } else if (mViewPlayState == PLAYING_PLAY_STATE) {
                    mPlayInstruction.pause();
                } else if (mViewPlayState == UNINIT_PLAY_STATE) {
                    mPlayInstruction.start();
                } else if (mViewPlayState == PAUSE_PLAY_STATE) {
                    mPlayInstruction.start();
                } else if (mViewPlayState == ERROR_PLAY_STATE) {
                    mPlayInstruction.start();
                }
            }
        } else if (v.getId() == R.id.videoView_functionMask_changeWindow_img) {
            if (mChangeWindowClickCallBack != null) {
                mChangeWindowClickCallBack.onClick(v);
            }
        }
    }

    /**
     * 竖屏的时候展示相关的功能按钮的方法
     */
    private void showNarrowStateFunView() {
        mTopBarView.setVisibility(VISIBLE);
        mButtomBarView.setVisibility(VISIBLE);

        mBackImg.setVisibility(GONE);
        mBatteryTv.setVisibility(GONE);
        mTimeTv.setVisibility(GONE);
        if (mPlayInstruction != null && mPlayInstruction.getPlayState() == FINISHED_PLAY_STATE) {
            mThumbImg.setVisibility(VISIBLE);
        } else {
            mThumbImg.setVisibility(GONE);
        }
        mTimeTv.setVisibility(VISIBLE);
        mPlaySeekBar.setVisibility(VISIBLE);
        mAllTimeTv.setVisibility(VISIBLE);
        mChangeWindowImg.setVisibility(VISIBLE);
    }

    /**
     * 横屏的时候展示相关的功能按钮的方法
     */
    private void showMaximumStateFunView() {
        mTopBarView.setVisibility(VISIBLE);
        mButtomBarView.setVisibility(VISIBLE);

        mBackImg.setVisibility(VISIBLE);
        mBatteryTv.setVisibility(VISIBLE);
        mTimeTv.setVisibility(VISIBLE);
        if (mPlayInstruction != null && mPlayInstruction.getPlayState() == FINISHED_PLAY_STATE) {
            mThumbImg.setVisibility(VISIBLE);
        } else {
            mThumbImg.setVisibility(GONE);
        }
        mTimeTv.setVisibility(VISIBLE);
        mPlaySeekBar.setVisibility(VISIBLE);
        mAllTimeTv.setVisibility(VISIBLE);
        mChangeWindowImg.setVisibility(VISIBLE);
    }

    /**
     * 悬浮的时候展示相关的功能按钮的方法
     */
    private void showFloatingStateFunView() {
        mTopBarView.setVisibility(VISIBLE);
        mButtomBarView.setVisibility(VISIBLE);

        mBackImg.setVisibility(GONE);
        mBatteryTv.setVisibility(GONE);
        mTimeTv.setVisibility(GONE);
        if (mPlayInstruction != null && mPlayInstruction.getPlayState() == FINISHED_PLAY_STATE) {
            mThumbImg.setVisibility(VISIBLE);
        } else {
            mThumbImg.setVisibility(GONE);
        }
        mTimeTv.setVisibility(VISIBLE);
        mPlaySeekBar.setVisibility(VISIBLE);
        mAllTimeTv.setVisibility(VISIBLE);
        mChangeWindowImg.setVisibility(VISIBLE);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void setBackImgClickCallBack(OnClickListener mBackImgClickCallBack) {
        this.mBackImgClickCallBack = mBackImgClickCallBack;
    }

    @Override
    public void setChangeWindowClickCallBack(OnClickListener mChangeWindowClickCallBack) {
        this.mChangeWindowClickCallBack = mChangeWindowClickCallBack;
    }

    @Override
    public void setPlayInstruction(IZVideoPlayInstruction mPlayInstruction) {
        this.mPlayInstruction = mPlayInstruction;
    }
}
