package com.gl.videoplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.danikula.videocache.HttpProxyCacheServer;
import com.gl.videoplayer.customview.VideoPlayer;

import butterknife.BindView;

public class ShowVideoActivity extends BaseActivity {
    String videoUrl;
    @BindView(R.id.videoPlayer)
    VideoPlayer videoPlayer;
    @BindView(R.id.tvPlay)
    TextView tvPlay;
    @BindView(R.id.itemTime)
    SeekBar itemTime;
    @BindView(R.id.tvCurrent)
    TextView tvCurrent;
    @BindView(R.id.tvDuration)
    TextView tvDuration;
    @BindView(R.id.linear_controll)
    LinearLayout linearControll;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    Handler UIhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                int currentProgress = videoPlayer.getCurrentPosition();
                changeTime(tvCurrent, currentProgress);//计时读秒
                itemTime.setProgress(currentProgress);//seekbar更新进度
                UIhandler.sendEmptyMessageDelayed(1, 10);//为了使seekbar移动平滑，每10毫秒更新一次
            } else if (msg.what == 0) {
                UIhandler.removeMessages(1);//取消更新进度
            }
        }
    };

    @Override
    protected int getLayoutID() {
        return R.layout.activity_show_video;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        videoUrl = getIntent().getStringExtra("videoUrl");
        //实现缓存加载
        HttpProxyCacheServer proxy = getApp().getProxy();
        String proxyUrl = proxy.getProxyUrl(videoUrl);
        videoPlayer.setVideoPath(proxyUrl);

        videoPlayer.setZOrderOnTop(true);
        videoPlayer.setZOrderMediaOverlay(true);

        progressBar.setVisibility(View.VISIBLE);

        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE);
        mediaController.setMediaPlayer(videoPlayer);
        //mediaController和videoview互相关联
        videoPlayer.setMediaController(mediaController);
        videoPlayer.start();//开始播放
        videoPlayer.requestFocus();
        videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
                changeTime(tvDuration, videoPlayer.getDuration());//显示video总时长的文本
                itemTime.setMax(videoPlayer.getDuration());//设置seekbar最大值为video的时长

                mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        //VideoView的seekTo是异步执行的，会有seek未完成但播放已经开始的现象。需要消除seekTo对恢复播放的影响，
                        // 应该在seek操作完成的seekComplete回调方法中执行start方法seekTo跳转的位置其实并不是参数所带的position，而是离position最近的关键帧,
                        if (!videoPlayer.isPlaying()) videoPlayer.start();
                    }
                });

                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        int secondpercent = (int) (videoPlayer.getDuration() * percent * 0.01f);
                        itemTime.setSecondaryProgress(secondpercent);//设置缓存进度
                    }
                });
            }
        });

        itemTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //seekbar开始拖动的时候，停止播放
                videoPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //seekbar,拖动结束时，进度设置到拖动处
                videoPlayer.seekTo(seekBar.getProgress());
            }
        });

        videoPlayer.setOnStatueListener(new VideoPlayer.onStatueListener() {
            @Override
            public void onStart() {
                tvPlay.setText("stop");
                UIhandler.sendEmptyMessage(1);
            }

            @Override
            public void onPause() {
                tvPlay.setText("start");
                UIhandler.sendEmptyMessage(0);
            }
        });

        videoPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoPlayer.start();//循环播放
                videoPlayer.requestFocus();
            }
        });

        tvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoPlayer.isPlaying()) {
                    videoPlayer.pause();
                } else {
                    videoPlayer.start();
                }
            }
        });
    }


    public void changeTime(TextView tv, int time) {
        int second = time / 1000;
        int hh = second / 3600;//一个小时3600
        int mm = second % 3600 / 60;//一个小时3600取余秒除以60为分钟
        int ss = second % 60;//60秒取余
        String str = String.format("%02d:%02d", mm, ss);//至少2位十进制整数
        tv.setText(str);
    }

    int currentTime;

    //videoview在退到后台或者被覆盖时记录播放时间，回来继续播放
    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.seekTo(currentTime);
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentTime = videoPlayer.getCurrentPosition();
        videoPlayer.pause();
    }
}
