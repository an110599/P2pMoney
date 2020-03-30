package com.ananhang.p2pmoney.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.ananhang.p2pmoney.common.MyApplication;

public class UIutils {
    public static Context getContextApplication(){
        return MyApplication.mcontext;
    }
    public static int dp2px(int dp){
       //dp转化为px
        float metrics = getContextApplication().getResources().getDisplayMetrics().density;
        //获取资源的getDisplayMetrics显示规则进行计算density为密度
        double v = dp * metrics + 0.5;
        return (int) v;
    }
    public static int px2dp(int px){
        //dp转化为px
        float metrics = getContextApplication().getResources().getDisplayMetrics().density;
        //获取资源的getDisplayMetrics显示规则进行计算density为密度
        double v = px / metrics + 0.5;
        return (int) v;
    }
}
