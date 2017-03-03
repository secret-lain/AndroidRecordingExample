package com.echo.view.presenter;

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
    MainActivity activity;


    @Override
    public void setView(View view) {
        activity = (MainActivity) view;
    }

    @Override
    public void onRecordStarted() {

    }

    @Override
    public void onRecordStopped() {

    }

    @Override
    public void sendRecord() {

    }
}
