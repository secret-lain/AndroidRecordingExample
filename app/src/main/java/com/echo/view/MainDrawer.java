package com.echo.view;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.echo.R;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.DimenHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by Lain on 2017-03-02.
 * Mikepenz MaterialDrawer Opensource.
 *
 * 빌더패턴을 사용한 오픈소스로, 구역별로 메소드를 나누어 코딩.
 */

public class MainDrawer {
    private Activity activity;
    private Drawer drawer;
    private DrawerBuilder builder;

    public void setView(Activity activity, Toolbar toolbar){
        this.activity = activity;

        builder = new DrawerBuilder(activity)
                .withToolbar(toolbar)
                .withSliderBackgroundColorRes(R.color.md_green_50)
                .withSelectedItem(-1)
                .withMultiSelect(false)
                .withCloseOnClick(true);

        inputDrawerItem();
        inputHeaderView();
        inputOnClick();

        drawer = builder.build();
    }

    private void inputOnClick() {
        builder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
             /*
             Position
             0 = 프로필 설정
             1 = 알람 설정
             2 = 어플리케이션 정보
             3 = 신고내역
             4 = 공유하기
             */
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                switch(position){
                    case 0:

                        break;
                }

                return false;
            }
        });
    }

    private void inputDrawerItem(){
        builder.addDrawerItems(
                new DividerDrawerItem(),
                new PrimaryDrawerItem().withIcon(R.drawable.account_setting).withName(R.string.drawer_account_setting).withSelectable(false),
                new PrimaryDrawerItem().withIcon(R.drawable.alarm_setting).withName(R.string.drawer_alarm).withSelectable(false),
                new PrimaryDrawerItem().withIcon(R.drawable.exclamation).withName(R.string.drawer_information).withSelectable(false),
                new PrimaryDrawerItem().withIcon(R.drawable.report).withName(R.string.drawer_report).withSelectable(false),
                new PrimaryDrawerItem().withIcon(R.drawable.share).withName(R.string.drawer_share).withSelectable(false)
        );
    }

    private void inputHeaderView(){
        builder.withHeader(R.layout.main_drawer_custom_header)
                .withHeaderHeight(DimenHolder.fromResource(R.dimen.profile_layout_height))
                .withHeaderDivider(true)
                .withHeaderPadding(true);
    }
}
