package com.ananhang.p2pmoney.common;

import android.content.Context;
import android.nfc.Tag;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
      /*
      *  单列模式
      *   处理全局异常的一个类
      * */


public class CaughtException implements Thread.UncaughtExceptionHandler {
    private static final String TAG ="CaughtException" ;
    private static CaughtException caughtException = null;

    private CaughtException() {

    }
    //只能通过getinstance来实例化对象
    public static  CaughtException getInstance(){
         if (caughtException==null){
             caughtException = new CaughtException();
         }
        return  caughtException;
    }
    private Context mContext;
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;
    public void init(Context context){
        this.mContext = context;
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(mUncaughtExceptionHandler);

    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        Log.e(TAG,"uncaughtException---uncaughtException"); //将这个布置到Application中
        //就能实现自动处理异常不会对界面进行报错 反馈在locat中；
        Looper.prepare();  //开启准备主线程的循环
        Toast.makeText(mContext,e.getMessage() , Toast.LENGTH_SHORT).show();
        Looper.loop();
        //但是必须在主线现在进行ui
        //有四种方法 一种handle+run  一种是异步
        // 还有一种手动开启主线程的looper，另外还有一种ui线程(只有继承了activity才可以)

    }
}
