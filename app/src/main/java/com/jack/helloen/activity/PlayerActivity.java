package com.jack.helloen.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jack.helloen.R;
import com.jack.helloen.util.AssetUtil;
import com.jack.helloen.util.ResourceUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class PlayerActivity extends BaseActivity {
    private final String TAG = "TAG";
    private Context mContext;
    private Button button_play, button_pause, button_stop;
    private SeekBar seek_bar;
    private MediaPlayer mPlayer = null;
    private boolean isRelease = true;
    private TextView playing_time, total_time;
    private TextView page_title, content_show;
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");
    final int interval_time = 1000; //时间间隔:1s
    private Handler handler;
    private Runnable runnable;
    private String str_title, str_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //设置返回按钮和处理
        ((TextView) findViewById(R.id.title_text)).setText("详情页");
        findViewById(R.id.title_left_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.title_left_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mContext = PlayerActivity.this;

        Intent intent = getIntent();
        str_title = intent.getStringExtra("title");
        str_index = intent.getStringExtra("index");

        initMedia(str_index);
        bindView();

        //定时任务，实现进度条移动效果
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (!isRelease || mPlayer.isPlaying()) {
                    Log.i(TAG, "" + mPlayer.getCurrentPosition());

                    //定时刷新对应的值
                    playing_time.setText(time.format(mPlayer.getCurrentPosition()));
                    seek_bar.setProgress(mPlayer.getCurrentPosition());
                }
                handler.postDelayed(this, interval_time);
            }
        };
        handler.postDelayed(runnable, interval_time); //触发定时
    }

    private void bindView() {
        page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText(str_title);

        playing_time = (TextView) findViewById(R.id.playing_time);
        total_time = (TextView) findViewById(R.id.total_time);

        button_play = (Button) findViewById(R.id.button_play);
        button_pause = (Button) findViewById(R.id.button_pause);
        button_stop = (Button) findViewById(R.id.button_stop);
        seek_bar = (SeekBar) findViewById(R.id.seek_bar);

        //初始化 控制
        button_play.setOnClickListener(clickListener);
        button_pause.setOnClickListener(clickListener);
        button_stop.setOnClickListener(clickListener);

        seek_bar.setProgress(mPlayer.getCurrentPosition());
        seek_bar.setMax(mPlayer.getDuration());
        seek_bar.setOnSeekBarChangeListener(seekBarChangeListener);

        //初始化 显示
        playing_time.setText(time.format(mPlayer.getCurrentPosition()));
        total_time.setText(time.format(mPlayer.getDuration()));

        //内容显示
        content_show = (TextView) findViewById(R.id.content_show);
        String str = AssetUtil.readFileString(mContext, str_index + "/content.txt");
        content_show.setText(str);
    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_play:
                    mPlayer.start();

                    button_play.setEnabled(false);
                    button_pause.setEnabled(true);
                    break;
                case R.id.button_pause:
                    if (mPlayer.isPlaying()) {
                        mPlayer.pause();

                        button_play.setEnabled(true);
                        button_pause.setEnabled(false);
                    }
                    break;
                case R.id.button_stop:
                    if (mPlayer.isPlaying()) {
                        mPlayer.reset();
                        playing_time.setText(time.format(0));
                        handler.removeCallbacks(runnable); //取消循环

                        isRelease = true;
                    }
                    break;
            }
        }
    };

    private void initMedia(String str_index) {
        if (isRelease) {
            try {
//                openRawMusic(str_index); //播放raw下mp3
                openAssetMusic(str_index); //播放assets下mp3

                isRelease = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //实现手动拖动进度条，音乐对应进度变化
            mPlayer.seekTo(seek_bar.getProgress());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    //读取asset内容
    private void openAssetMusic(String index) throws IOException {
//        String fileName = "a001.mp3"; //根目录文件
        String fileName = index + "/" + index + ".mp3"; //子目录文件
        AssetFileDescriptor fd = getAssets().openFd(fileName);
        mPlayer = new MediaPlayer();
        mPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
        mPlayer.prepare();
    }

    //读取raw文件
    private void openRawMusic(String index) throws IOException {
        int resourceId = ResourceUtil.getResId(index, R.raw.class);//读取raw文件
        mPlayer = MediaPlayer.create(mContext, resourceId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            handler.removeCallbacks(runnable);
        }
    }


}
