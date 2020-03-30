package com.ananhang.p2pmoney.common;

import android.app.Application;
import android.content.Context;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MyApplication extends Application {
    public static Context mcontext;
    public static Handler mhandler;
    public static Thread mmainThread;
   //需要在项目清单中进行配置 name：
    @Override
    public void onCreate() {
        super.onCreate();
        mcontext = getApplicationContext();
        mmainThread = Thread.currentThread();
/*
        CaughtException.getInstance().init(this);   //一般发布时候用现在为了看到问题不使用
*/
    }
}
