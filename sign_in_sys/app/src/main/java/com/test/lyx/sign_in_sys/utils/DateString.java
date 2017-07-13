package com.test.lyx.sign_in_sys.utils;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class DateString {
    private static String mWay;
    private static Calendar c;

    public static String StringXq() {

        c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));

        if ("1".equals(mWay)) {
            mWay = "星期天";
        } else if ("2".equals(mWay)) {
            mWay = "星期一";
        } else if ("3".equals(mWay)) {
            mWay = "星期二";
        } else if ("4".equals(mWay)) {
            mWay = "星期三";
        } else if ("5".equals(mWay)) {
            mWay = "星期四";
        } else if ("6".equals(mWay)) {
            mWay = "星期五";
        } else if ("7".equals(mWay)) {
            mWay = "星期六";
        }
       return mWay;

    }

    public static String StringDate(){


        return c.get(Calendar.YEAR) + "年 " + (c.get(Calendar.MONTH)+1) + "月 " + c.get(Calendar.DAY_OF_MONTH) + "日 ";



    }



}
