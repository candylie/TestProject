package com.zk.framework.view.videoplay;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.zk.framework.view.videoplay.i.IZVideoPlayInstruction;
import com.zk.framework.view.videoplay.mask.IZBaseVideoFunMaskView;
import com.zk.framework.view.videoplay.mask.ZVideoSimpleFunMaskView;
import com.zk.framework.view.videoplay.util.ZVideoPlayControl;

import static com.zk.framework.view.videoplay.constant.ZVideoConstant.*;

/**
 * -视频播放器
 *
 * @author 张科
 * @date 2019/3/18.
 */
public class ZVideoView extends FrameLayout implements IZVideoPlayInstruction,
        SurfaceHolder.Callback, TextureView.SurfaceTextureListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener {
    /**
     * 当前播放器展示状态
     */
    private int mViewShowState = NARROW_SHOW_STATE;

    /**
     * 当前播放器播放状态
     */
    private int mViewPlayState = UNINIT_PLAY_STATE;

    private Surface mSurface;
    // 方式1,由于SurfaceView是单独的线程进行视频播放,如果放在列表中,进行滑动,就会出现黑边现象
    // SurfaceView 不支持变换 如旋转拉伸等, 但是因为是在其他线程刷新View所以不会卡UI线程,其次占用cpu低
    // 可以硬解码, 占用内存低, 所以用于单独的界面播放是比较好的选择
    /**
     * 播放view
     */
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    // 方式2 TextureView是在主线程中绘制的,放在列表中展示不会出现黑边问题,但是内存占用搞,可能卡UI等,使用的地方
    // 必须要开启硬件加速
    /**
     * 播放view
     */
    private TextureView mTextureView;
    private SurfaceTexture mSurfaceTexture;
    /**
     * 是否初始化过视频展示控件
     */
    private boolean isInitDisplayView = false;
    /**
     * 功能蒙版view
     */
    private IZBaseVideoFunMaskView mMaskView;

    private MyHandler mHandler;

    private ZVideoPlayControl.ZVideoParamBuilder mBuilder;

    private long mVideoAllTime;
    private long mNowPlayingTime;

    /**
     * 返回按钮点击事件
     */
    private OnClickListener mBackImgClickCallBack;
    private OnClickListener mChangeWindowClickCallBack;
    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener;

    public ZVideoView(@NonNull Context context) {
        this(context, null);
    }

    public ZVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //添加顶部的功能按钮的蒙版布局
        initFunctionMaskView(context);
        //initHandler
        mHandler = new MyHandler();
    }


    /**
     * 初始化SurfaceViewView
     *
     * @param context -
     */
    private void initSurfaceView(Context context) {
        if (mSurfaceView == null && !isInitDisplayView) {
            mSurfaceView = new SurfaceView(context);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mSurfaceView.setLayoutParams(layoutParams);
            mSurfaceHolder = mSurfaceView.getHolder();
            mSurface = mSurfaceHolder.getSurface();
            mSurfaceHolder.addCallback(this);
            mSurfaceHolder.setKeepScreenOn(true);
            addView(mSurfaceView, 0);
            isInitDisplayView = true;
        }
    }

    private void initTextureView(Context context) {
        if (mTextureView == null && !isInitDisplayView) {
            mTextureView = new TextureView(context);
            mTextureView.setSurfaceTextureListener(this);
            addView(mTextureView, 0);
        }
    }

    /**
     * 添加功能蒙版布局
     *
     * @param context -
     */
    private void initFunctionMaskView(Context context) {
        mMaskView = new ZVideoSimpleFunMaskView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        ((ZVideoSimpleFunMaskView) mMaskView).setLayoutParams(layoutParams);
        addView(((ZVideoSimpleFunMaskView) mMaskView));
        mMaskView.setPlayInstruction(this);
    }


    /**
     * 开始播放 但是要初始化播放器完成后才能start播放
     */
    @Override
    public void start(ZVideoPlayControl.ZVideoParamBuilder builder) {
        if (builder == null && mBuilder != null) {
            //表示从暂停回到继续播放的状态
        } else if (builder != null && mBuilder == null) {
            //表示初次播放 并设置播放相关的参数
            mBuilder = builder;
            if (!isInitDisplayView) {
                if (builder.isUserTextureView()) {
                    initTextureView(getContext());
                } else {
                    initSurfaceView(getContext());
                }
            }
        } else if (builder == null) {
            //表示外界调用错误 因为播放器还没初始化
            Toast.makeText(getContext(), "播放器加载错误", Toast.LENGTH_SHORT).show();
            return;
        }
        ZVideoPlayUtil.readyPlay(mViewShowState, this);
    }

    /**
     * 暂停播放
     */
    @Override
    public void pause() {
        ZVideoPlayUtil.pause(mViewShowState, this);
    }

    /**
     * 重播
     */
    @Override
    public void replay() {
        ZVideoPlayUtil.replay(mViewShowState, this);
    }

    @Override
    public void jumpTimestamp(long timestamp) {

    }

    @Override
    public void delayHideMaskView() {
        mHandler.sendMessageDelayed(Message.obtain(getHandler(), HIDE_MESSAGE_ACTION, mMaskView), 3000);
    }

    @Override
    public int getPlayState() {
        return mViewPlayState;
    }

    @Override
    public ZVideoPlayControl.ZVideoParamBuilder getBuilder() {
        return mBuilder;
    }

    @Override
    public void setPlayState(int playState) {
        mViewPlayState = playState;
    }

    @Override
    public long getNowPlayingTimestamp() {
        return mNowPlayingTime;
    }

    @Override
    public Surface getSurface() {
        return mSurface;
    }

    /**
     * 设置自定义功能蒙版
     *
     * @param maskView -
     */
    public void setMaskView(@NonNull IZBaseVideoFunMaskView maskView) {
        if (mMaskView != null) {
            removeView((View) mMaskView);
        }
        this.mMaskView = maskView;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        ((ZVideoSimpleFunMaskView) mMaskView).setLayoutParams(layoutParams);
        mMaskView.setPlayInstruction(this);
        mMaskView.setBackImgClickCallBack(mBackImgClickCallBack);
        mMaskView.setChangeWindowClickCallBack(mChangeWindowClickCallBack);
        addView((View) mMaskView);
    }

    public IZBaseVideoFunMaskView getMaskView() {
        return mMaskView;
    }

    public void setBackImageViewClickCallBack(View.OnClickListener clickCallBack) {
        mBackImgClickCallBack = clickCallBack;
    }

    public void setChangeWindowViewClickCallBack(View.OnClickListener clickCallBack) {
        mChangeWindowClickCallBack = clickCallBack;
    }

    public void setSeekBarChangeListener(SeekBar.OnSeekBarChangeListener mSeekBarChangeListener) {
        this.mSeekBarChangeListener = mSeekBarChangeListener;
    }
    //    surfaceView的三个回调 start

    /**
     * surfaceView的三个回调 start
     *
     * @param holder -
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("zk", "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e("zk", "surfaceChanged,format=" + format + ",width=" + width + ",height=" + height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("zk", "surfaceDestroyed");
    }
//    surfaceView的三个回调 end

//    textureView的四个回调 start

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.e("zk", "onSurfaceTextureAvailable,width=" + width + ",height=" + height);
        if (mSurfaceTexture == null) {
            mSurfaceTexture = surface;
            if (mSurface != null) {
                mSurface.release();
            } else {
                mSurface = new Surface(mSurfaceTexture);
            }
        } else {
            mTextureView.setSurfaceTexture(mSurfaceTexture);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.e("zk", "onSurfaceTextureSizeChanged,width=" + width + ",height=" + height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.e("zk", "onSurfaceTextureDestroyed");
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        Log.e("zk", "onSurfaceTextureUpdated");

    }

//    textureView的四个回调 End
//    加载状态的三个回调 start

    /**
     * 播放器初始化完成
     *
     * @param mp -
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        ZVideoPlayUtil.start(mViewShowState, mp, this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        //加载失败
        ZVideoPlayUtil.playError(mViewShowState, this);
        return false;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }
//    加载状态的三个回调 end

    private static class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HIDE_MESSAGE_ACTION:
                    if (msg.obj != null) {
                        ((IZBaseVideoFunMaskView) msg.obj).hideFunView();
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
