package com.echo.interfaces;

/**
 * Created by s.Lain on 2017-02-23.
 * MVP 패턴 규칙을 위한 인터페이스.
 *
 * 이 부분에서 구현되는 것은 아래와 같다.
 * 1. presenter와의 attach, (detach)
 * 2. presenter에서 동작할 메소드
 * 3. view 자체
 * 3-1. view 내부에서 사용할 메소드
 */

/**
 * MainActivity를 위한 Presenter Interface
 * Presenter는 뷰를 설정하고 아래의 클래스를 다룬다.
 * 1. RecordController
 * 2. MessageCloud Data Check (received Message)
 * ...
 *
 * View는 아래와 같은 동작을 다룬다.
 * 1. 녹음 레이아웃 열고닫기
 * 2. 메세지 확인 레이아웃 열고닫기
 * 3. 세팅버튼클릭동작(Intent 처리할것같은데)
 *
 * 2017-03-03
 * 크게 세부분으로 나눌 수 있겠다.
 * Drawer -> View -> Presenter로 오는 Intent 로직
 * Recording 로직
 * Sending 로직
 */
public interface MainPresenter {
    void setView(MainPresenter.View view);
    void onRecordStarted();
    void onRecordStopped();
    void sendRecord();

    interface View{
        void initView();
        void onRecordStarted();
        void onRecordStopped();
        void onProgressUpdated(int progress);
        void onPlayStarted();
        void onPlayStopped();
        void onPageFlipped(boolean toMessageCheckLayout);
        void onSendButtonClicked();
    }
}
