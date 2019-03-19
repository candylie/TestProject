package com.zk.framework.view.videoplay.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.zk.framework.app.ZApp;
import com.zk.framework.view.videoplay.ZVideoView;

import java.util.ArrayList;
import java.util.Objects;

import static com.zk.framework.view.videoplay.constant.ZVideoConstant.*;

/**
 * -用来操作MediaPlayer的工具
 *
 * @author 张科
 * @date 2019/3/19.
 */
public class ZVideoPlayMediaManager {

    private static ZVideoPlayMediaManager manager;
    private ArrayList<MediaPlayer> cacheList = new ArrayList<>(1);
    private MediaPlayer player;
    private int mVolume;

    private ZVideoPlayMediaManager() {
        try {
            AudioManager mAudioManager = (AudioManager) (ZApp.getNowActivity()).getSystemService(Context.AUDIO_SERVICE);
            mVolume = Objects.requireNonNull(mAudioManager).getStreamVolume(AudioManager.STREAM_MUSIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ZVideoPlayMediaManager obtain() {
        synchronized (Object.class) {
            if (manager == null) {
                manager = new ZVideoPlayMediaManager();
            }
            return manager;
        }
    }
    //Idle 状态：当使用new()方法创建一个MediaPlayer对象或者调用了其reset()方法时，该MediaPlayer对象处于idle状态。
    //End 状态：通过release()方法可以进入End状态，只要MediaPlayer对象不再被使用，就应当尽快将其通过release()方法释放掉
    //Initialized 状态：这个状态比较简单，MediaPlayer调用setDataSource()方法就进入Initialized状态，表示此时要播放的文件已经设置好了。
    //Prepared 状态：初始化完成之后还需要通过调用prepare()或prepareAsync()方法，这两个方法一个是同步的一个是异步的，只有进入Prepared状态，
    // 才表明MediaPlayer到目前为止都没有错误，可以进行文件播放。

    /**
     * 准备播放器
     *
     * @param videoView -
     * @param builder   -
     */
    public void readyPlay(ZVideoView videoView, ZVideoPlayControl.ZVideoParamBuilder builder) {
        Exception exp = null;
        try {
            videoView.setPlayState(PREPARING_PLAY_STATE);
            if (player == null) {
                player = new MediaPlayer();
                player.setSurface(videoView.getSurface());
                //设置播放器的状态监听
                player.setOnPreparedListener(videoView);
                //播放完成的监听
                player.setOnCompletionListener(videoView);
                //播放器播放失败监听
                player.setOnErrorListener(videoView);
                //设置播放类型
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                //设置进度的监听 todo ??
                player.setOnSeekCompleteListener(videoView);
                //设置是否一直保持屏幕开启
                player.setScreenOnWhilePlaying(true);
                //设置音量大小
                player.setVolume(mVolume, mVolume);
            } else {
                player.reset();
            }
            //设置资源文件地址
            player.setDataSource(builder.getVideoURL());
            //是否循环播放
            player.setLooping(builder.isLooping());
            //初始化完成
            player.prepareAsync();
            videoView.setPlayState(PREPARED_PLAY_STATE);
        } catch (Exception e) {
            exp = e;
        }
        if (exp != null) {
            exp.printStackTrace();
            if (videoView != null) {
                videoView.onError(player, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            }
            player.release();
            player = null;
        }
    }

    /**
     * 重试
     */
    private void tryAgain(ZVideoView videoView, Exception e) {
        e.printStackTrace();
        //重新初始化
        readyPlay(videoView, videoView.getBuilder());
    }


    /**
     * 开始播放
     *
     * @param mp        -
     * @param videoView -
     */
    public void start(MediaPlayer mp, ZVideoView videoView) {
        if (mp == player) {
            if (!mp.isPlaying()) {
                try {
                    mp.start();
                } catch (Exception e) {
                    tryAgain(videoView, e);
                }
            }
        }
    }

    public void pause(ZVideoView videoView) {
        if (player != null) {
            try {
                player.pause();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

}