package com.echo.view;

import android.app.Activity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

/**
 * Created by Lain on 2017-03-02.
 */

public class MainDrawer {
    private Activity activity;
    private Drawer drawer;

    public void setView(Activity activity, Toolbar toolbar){
        this.activity = activity;

        drawer = new DrawerBuilder(activity)
                .withToolbar(toolbar)
                .build();
    }


}
