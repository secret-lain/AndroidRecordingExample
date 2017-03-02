package com.echo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dd.processbutton.FlatButton;
import com.echo.R;
import com.echo.interfaces.MainPresenter;
import com.gc.materialdesign.views.ProgressBarDeterminate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainPresenter.View, View.OnClickListener{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.record_button) ImageView recordButton;
    @BindView(R.id.play_button) ImageView playButton;
    @BindView(R.id.record_progressbar) ProgressBarDeterminate progressBar;
    @BindView(R.id.record_progress_textview) TextView progressText;
    @BindView(R.id.send_button) FlatButton sendButton;
    boolean isPlayButtonExpanded;
    boolean isProgressVisible;
    boolean isRecording;
    boolean isPlaying;
    MainDrawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        initView();
    }


    @Override
    public void initView() {
        isPlayButtonExpanded = false;
        isProgressVisible = false;
        drawer = new MainDrawer();
        drawer.setView(this, toolbar);


        Glide.with(MainActivity.this)
                .load(R.drawable.stop_64px_blue400)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .preload();
        Glide.with(MainActivity.this)
                .load(R.drawable.recording_64px_red400)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .preload();
    }

    /**
     * 1. 초기상태의 경우 = Progress Expand
     * 2. 재녹음의 경우 = playButton Collapse
     */
    @Override
    public void onRecordStarted() {
        isRecording = true;

        //Progress 텍스트가 없다면 연다.
        if(!isProgressVisible)
            progressLayoutAnimated(true);

        //플레이버튼이 열려있다면 닫는다.
        if(isPlayButtonExpanded)
            playLayoutAnimated(false);

        Glide.with(this)
                .load(R.drawable.stop_64px_blue400)
                .dontAnimate()
                .into(recordButton);
    }

    @Override
    public void onRecordStopped() {
        isRecording = false;

        //Progress 텍스트가 있다면 없앤다
        if(isProgressVisible)
            progressLayoutAnimated(false);

        //플레이버튼이 없다면 연다
        if(!isPlayButtonExpanded)
            playLayoutAnimated(true);

        Glide.with(this)
                .load(R.drawable.recording_64px_red400)
                .dontAnimate()
                .into(recordButton);
    }

    @Override
    public void onProgressUpdated(int progress) {

    }

    @Override
    public void onPlayStarted() {

    }

    @Override
    public void onPlayStopped() {

    }

    @Override
    public void onSendButtonClicked() {

    }

    /**
     * 애니메이션을 주면서 record_layout 에 playButton visibility를 toggle한다.
     * @param expanding true = playButton 출현
     *                false = playButton 숨김
     */
    private void playLayoutAnimated(boolean expanding){
        Animation playButtonShow = AnimationUtils.loadAnimation(this, R.anim.main_play_button_show);
        Animation playButtonHide = AnimationUtils.loadAnimation(this, R.anim.main_play_button_hide);
        Animation recordButtonShow = AnimationUtils.loadAnimation(this, R.anim.main_record_button_show);
        Animation recordButtonHide = AnimationUtils.loadAnimation(this, R.anim.main_record_button_hide);

        if(expanding && playButton.getVisibility() == View.GONE){
            isPlayButtonExpanded = !isPlayButtonExpanded;
            /*
            1. 왼쪽으로 서서히 생기는 플레이버튼 애니메이션
            1. 오른쪽으로 이동하는 녹음버튼 애니메이션
            2. 플레이버튼 visible
             */
            playButton.startAnimation(playButtonShow);
            playButton.setVisibility(View.VISIBLE);

            recordButton.startAnimation(recordButtonShow);
        } else if(!expanding && playButton.getVisibility() == View.VISIBLE){
            isPlayButtonExpanded = !isPlayButtonExpanded;
            /*
            1. 오른쪽으로 서서히 사라지는 플레이버튼 애니메이션
            1. 왼쪽으로 이동하는 녹음버튼 애니메이션
            3. 플레이버튼 gone
             */
            playButton.startAnimation(playButtonHide);
            playButton.setVisibility(View.GONE);

            recordButton.startAnimation(recordButtonHide);
        }
    }

    /**
     * ProgressText 를 출현시킨다.
     *
     * @param visible true = ProgressText 보임
     *                 false = ProgressText 가림
     */
    private void progressLayoutAnimated(boolean visible){
        if(visible){
            isProgressVisible = !isProgressVisible;
            progressText.setVisibility(View.VISIBLE);
        } else{
            isProgressVisible = !isProgressVisible;
            progressText.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.record_button, R.id.play_button, R.id.send_button})
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.record_button:
                if(!isRecording)
                    onRecordStarted();
                else
                    onRecordStopped();
                break;
            case R.id.play_button:
                if(!isPlaying)
                    onPlayStarted();
                else
                    onPlayStopped();
                break;

        }
    }
}
