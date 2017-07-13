package com.test.lyx.sign_in_sys;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

public class BasicActivity extends AppCompatActivity {





    /**
     * 检测网络是否可用
     * @return boolean
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
    }


    public void setCompletePic(View view, int res) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
        int bwidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();

        //获取手机屏幕宽度
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = width * bHeight / bwidth;

        ViewGroup.LayoutParams para = view.getLayoutParams();
        //高度设置为图片自身高度
        para.height = height;
        view.setLayoutParams(para);


    }








}
