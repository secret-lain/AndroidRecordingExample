package com.echo.view.presenter;

import com.echo.common.record.RecordController;
import com.echo.interfaces.MainPresenter;
import com.echo.view.MainActivity;

/**
 * Created by admin on 2017-03-03.
 *
 * 크게 세부분으로 나눌 수 있겠다.
 * Drawer -> View -> Presenter로 오는 Intent 로직
 * Recording 로직
 * Sending 로직
 *
 */

public class MainPresenterImpl implements MainPresenter{
    RecordController controller;
    MainActivity activity;


    @Override
    public void setView(View view) {
        activity = (MainActivity) view;
        controller = new RecordController(activity);
    }

    @Override
    public void onRecordStarted() {
        controller.recordStart(new RecordController.recordCallback() {
            @Override
            public void recordingStarted() {
                activity.onRecordStarted();
            }

            @Override
            public void recordingEnded() {
                onRecordStopped();
            }

            @Override
            public void onProgress(int second) {
                activity.onProgressUpdated(second);
            }
        });
    }

    @Override
    public void onRecordStopped() {
        controller.recordStop();
        activity.onRecordStopped();
    }

    @Override
    public void onPlayStarted() {
        controller.playAudio();
        activity.onPlayStarted();
    }

    @Override
    public void onPlayStopped() {
        controller.destroyPlayer();
        activity.onPlayStopped();
    }

    @Override
    public void sendRecord() {

    }
}
