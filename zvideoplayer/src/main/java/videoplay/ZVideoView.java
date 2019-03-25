package videoplay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import videoplay.i.IZVideoPlayInstruction;
import videoplay.mask.IZBaseVideoFunMaskView;
import videoplay.mask.ZVideoSimpleFunMaskView;
import videoplay.util.ZVideoPlayControl;

import static videoplay.constant.ZVideoConstant.*;


/**
 * -视频播放器
 *
 * @author 张科
 * @date 2019/3/18.
 */
public class ZVideoView extends FrameLayout implements IZVideoPlayInstruction,
        SurfaceHolder.Callback, TextureView.SurfaceTextureListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnBufferingUpdateListener {
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

    private GestureDetector gestureDetector;

    @SuppressLint("HandlerLeak")
    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HIDE_MASK_VIEW_MESSAGE_ACTION:
                    ZVideoPlayUtil.hideMaskBarView(mViewPlayState, ZVideoView.this);
                    if (mViewPlayState != PREPARING_PLAY_STATE &&
                            mViewPlayState != PAUSE_PLAY_STATE &&
                            mViewPlayState != FINISHED_PLAY_STATE &&
                            mViewPlayState != LOADING_PLAY_STATE &&
                            mViewPlayState != ERROR_PLAY_STATE) {
                        //以上状态蒙版中的播放状态的view需要显示
                        ZVideoPlayUtil.hideMaskStateBTView(mViewShowState, mViewPlayState, ZVideoView.this);
                    }
                    break;
                case SHOW_MASK_VIEW_MESSAGE_ACTION:
                    ZVideoPlayUtil.showMaskBarView(mViewShowState, mViewPlayState, ZVideoView.this);
                    ZVideoPlayUtil.showMaskStateView(mViewShowState, mViewPlayState, ZVideoView.this);
                    break;
                case CHANGE_PLAY_STATE_MESSAGE_ACTION:
                    if (mViewPlayState == PLAYING_PLAY_STATE) {
                        ZVideoPlayUtil.pause(mViewShowState, ZVideoView.this);
                        ZVideoPlayUtil.showMaskStateView(mViewShowState, mViewPlayState, ZVideoView.this);
                    } else if (mViewPlayState == PAUSE_PLAY_STATE) {
                        ZVideoPlayUtil.start(mViewShowState, ZVideoView.this);
                        delayHideMaskView();
                    } else if (mViewPlayState == UNINIT_PLAY_STATE) {
                        start();
                    }
                    //todo 当如果当前为加载中的状态的时候 如果用户双击, 这里应该保存一个操作记录,表示用户暂时不想加载完毕自动播放
                    break;
                case MEDIA_PLAY_PREPARING_MESSAGE_ACTION:
                    //播放器初始化
                    ZVideoPlayUtil.preparing(mViewShowState, ZVideoView.this);
                    break;
                case PLAY_FINISH_MESSAGE_ACTION:
                case PLAY_ERROR_MESSAGE_ACTION:
                    //播放器初始化
                    ZVideoPlayUtil.playCompletion(mViewShowState, ZVideoView.this);
                    break;
                default:
                    break;
            }
        }
    }

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
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener());
        //双击监听
        gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.i("zk", "onSingleTapConfirmed");
                //单击完成
                //判断是否播放完成 如果播放完成或者播放错误 那么就不能展示功能蒙版等了
                if (mViewPlayState == ERROR_PLAY_STATE ||
                        mViewPlayState == FINISHED_PLAY_STATE) {
                    return false;
                } else {
                    //切换蒙版展示状态
                    if (mMaskView.isShowingBarView()) {
                        //表示单击时间 1.显示功能面板
                        mHandler.handleMessage(Message.obtain(getHandler(), HIDE_MASK_VIEW_MESSAGE_ACTION));
                    } else {
                        mHandler.handleMessage(Message.obtain(getHandler(), SHOW_MASK_VIEW_MESSAGE_ACTION));
                    }
                }
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.i("zk", "onDoubleTap");
                //双击完成
                if (mViewPlayState == ERROR_PLAY_STATE ||
                        mViewPlayState == FINISHED_PLAY_STATE) {
                    return false;
                } else {
                    mHandler.handleMessage(Message.obtain(getHandler(), CHANGE_PLAY_STATE_MESSAGE_ACTION));
                }
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
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
    @Deprecated
    public void start(ZVideoPlayControl.ZVideoParamBuilder builder) {
        if (builder == null && mBuilder != null) {
            //表示从暂停回到继续播放的状态
            ZVideoPlayUtil.start(mViewShowState, this);
        } else if (builder != null && mBuilder == null) {
            //表示初次播放 初始化播放View, 并设置播放相关的参数
            //当播放View初始化完成后,就可以开始设置播放器相关的配置了
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
        }
    }

    /**
     * 开始播放 但是要初始化播放器完成后才能start播放
     */
    @Override
    public void start() {
        if (mBuilder != null) {
            if (mViewPlayState == UNINIT_PLAY_STATE) {
                if (!isInitDisplayView) {
                    if (mBuilder.isUserTextureView()) {
                        initTextureView(getContext());
                    } else {
                        initSurfaceView(getContext());
                    }
                }
            } else if (mViewPlayState == PAUSE_PLAY_STATE) {
                ZVideoPlayUtil.start(mViewShowState, this);
            } else {
                Toast.makeText(getContext(), "播放器调用错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 暂停播放
     */
    @Override
    public void pause() {
        ZVideoPlayUtil.pause(mViewShowState, this);
        ZVideoPlayUtil.showMaskBarView(mViewShowState, PAUSE_PLAY_STATE, this);
        //延时消失功能蒙版View
        delayHideMaskView();
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
        //先取消之前的延时任务
        mHandler.removeMessages(HIDE_MASK_VIEW_MESSAGE_ACTION);
        //重新延时消失
        mHandler.sendMessageDelayed(Message.obtain(getHandler(), HIDE_MASK_VIEW_MESSAGE_ACTION), 3000);
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
    public ZVideoView setBuilder(ZVideoPlayControl.ZVideoParamBuilder builder) {
        this.mBuilder = builder;
        return this;
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
        //播放的SurfaceTexture初始化完成了
        if (mSurfaceTexture == null) {
            //表示是初次初始化完成
            mSurfaceTexture = surface;
            if (mSurface != null) {
                mSurface.release();
            } else {
                mSurface = new Surface(mSurfaceTexture);
            }
            //通知初始化播放器
            mHandler.handleMessage(Message.obtain(getHandler(), MEDIA_PLAY_PREPARING_MESSAGE_ACTION));
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
     * 播放器初始化完成,然后开始播放
     *
     * @param mp -
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        ZVideoPlayUtil.start(mViewShowState, this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mHandler.handleMessage(Message.obtain(getHandler(), PLAY_FINISH_MESSAGE_ACTION));
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e("zk", "onError what=" + what + ",extra=" + extra);
        //加载失败
        mHandler.handleMessage(Message.obtain(getHandler(), PLAY_ERROR_MESSAGE_ACTION));
        return false;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    /**
     * 缓冲回调
     *
     * @param mp      -
     * @param percent -
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.e("zl", "onBufferingUpdate percent缓冲" + percent);
    }


    //    加载状态的三个回调 end

    private boolean isTriggerSeekMove = false;
    private float downX, downY;
    private long lastDownTime = 0;

    /**
     * 手势监听
     *
     * @param event -
     * @return -
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTriggerSeekMove = false;
                downX = event.getX();
                downY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                //先判断是否是上下滑动 再判断是否是快进快退
                if (Math.abs(x - downX) > 50) {
                    //表示是上下滑动 1.显示音量或者亮度的调整的框框
                    isTriggerSeekMove = true;
                    return true;
                } else if (Math.abs(y - downY) > 50) {
                    //表示是左右滑动 1.显示功能面板 2显示快进快退的框框
                    isTriggerSeekMove = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //判断up-down的事件长短,并且判断是否触发了快进快退
//                long upTime = event.getEventTime();
//                if (!isTriggerSeekMove && (upTime - event.getDownTime()) < 500) {
//                    //先判断是否是双击
//                    if (event.getDownTime() - lastDownTime < 200) {
//                        //表示双击 改变当前播放状态
//                        mHandler.handleMessage(Message.obtain(getHandler(), CHANGE_PLAY_STATE_MESSAGE_ACTION));
//                        lastDownTime = 0;
//                    } else {
//                        if (mMaskView.isShowingBarView()) {
//                            //表示单击时间 1.显示功能面板
//                            mHandler.handleMessage(Message.obtain(getHandler(), HIDE_MASK_VIEW_MESSAGE_ACTION));
//                        } else {
//                            mHandler.handleMessage(Message.obtain(getHandler(), SHOW_MASK_VIEW_MESSAGE_ACTION));
//                        }
//                        lastDownTime = event.getDownTime();
//                    }
//                }
                isTriggerSeekMove = false;
                return super.onTouchEvent(event);
            case MotionEvent.ACTION_CANCEL:
                isTriggerSeekMove = false;
                return super.onTouchEvent(event);
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
