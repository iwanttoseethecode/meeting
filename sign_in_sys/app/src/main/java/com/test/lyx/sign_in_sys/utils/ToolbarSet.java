package com.test.lyx.sign_in_sys.utils;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.test.lyx.sign_in_sys.BasicActivity;
import com.test.lyx.sign_in_sys.R;


/**
 * Created by lyx on 2016/9/28 0028.
 */
public class ToolbarSet {
    //用一个类设置每一个页面的ToolBar的样式，减小代码开销
    public static void setToolbar(final BasicActivity activity, Toolbar toolbar, String title) {
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        Drawable upArrow = activity.getResources().getDrawable(R.drawable.arrow_new);
        upArrow.setColorFilter(activity.getResources().getColor(R.color.milky_white), PorterDuff.Mode.SRC_ATOP);


        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            activity.getSupportActionBar().setHomeAsUpIndicator(upArrow);

        }

        toolbar.setTitle(title);
        toolbar.setTitleTextColor(activity.getResources().getColor(R.color.milky_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                activity.onBackPressed();
                activity.finish();

            }
        });


    }


}
