package com.echo.common;

import android.app.Activity;
import android.widget.Toast;

import com.echo.R;

/**
 * Created by admin on 2016-11-13.
 */

public class BackPressCloseHandler {

    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            //activity.finish();
            activity.finishAffinity();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity,activity.getString(R.string.backpress_guide), Toast.LENGTH_SHORT);
        toast.show();
    }
}