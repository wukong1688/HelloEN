package com.jack.helloen.activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jack.helloen.R;

public class TestMediaPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private MediaPlayer mPlayer = null;
    private boolean isRelease = true;
    private Button btn_play_audio, btn_stop_audio, btn_pause_audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_media);

        mContext = TestMediaPlayerActivity.this;
        bindView();
    }

    private void bindView() {
        btn_play_audio = (Button) findViewById(R.id.btn_play_audio);
        btn_stop_audio = (Button) findViewById(R.id.btn_stop_audio);
        btn_pause_audio = (Button) findViewById(R.id.btn_pause_audio);
        btn_play_audio.setOnClickListener(this);
        btn_stop_audio.setOnClickListener(this);
        btn_pause_audio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play_audio:
                if (isRelease) {
                    mPlayer = MediaPlayer.create(mContext, R.raw.a002);
                    isRelease = false;
                }
                mPlayer.start();
                btn_play_audio.setEnabled(false);
                btn_pause_audio.setEnabled(true);
                btn_stop_audio.setEnabled(true);
                break;
            case R.id.btn_pause_audio:
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    btn_play_audio.setEnabled(true);
                    btn_pause_audio.setEnabled(false);
                    btn_stop_audio.setEnabled(false);
                }
                break;
            case R.id.btn_stop_audio:
                if (mPlayer.isPlaying()) {
                    mPlayer.reset();
                    isRelease = true;

                    btn_play_audio.setEnabled(true);
                    btn_pause_audio.setEnabled(false);
                    btn_stop_audio.setEnabled(false);
                }
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPlayer != null){
            mPlayer.stop();
            mPlayer.release();
        }
    }
}
