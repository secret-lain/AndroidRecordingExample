package com.echo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.floatingActionButton)FloatingActionButton fab;
    @BindView(R.id.recondingLayout)CardView recordingLayout;
    @BindView(R.id.toolbar)Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        fab.setOnClickListener(this);
        recordingLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.floatingActionButton:
                //fab을 클릭하면 fab는 오른쪽으로 사라지고 layout은 위로 올라온다.
                Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);//for recordLayout
                Animation slideLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left);//for fab

                view.startAnimation(slideLeft);
                view.setVisibility(View.GONE);

                recordingLayout.startAnimation(slideUp);
                recordingLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.recondingLayout:
                //layout을 터치하면 fab는 왼쪽에서 나타나고 layout은 아래로 내려간다.
                Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);//for recordLayout
                Animation slideRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right);//for fab

                fab.startAnimation(slideRight);
                fab.setVisibility(View.VISIBLE);

                view.startAnimation(slideDown);
                view.setVisibility(View.GONE);
                break;
        }
    }
}
