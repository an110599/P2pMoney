package com.ananhang.p2pmoney.common;

import android.app.Activity;

import java.util.Stack;

/*
       *       统一app的任务栈管理先进后出
       *
       *      添加，删除，删除当前 删除所有 求栈的大小
       *
       *      Android中的单例模式
定义：

     单例模式：确保某一个类只有一个实例，而且自行实例化并向整个系统提供这个实例。

      使用场景：

      确保某一个类有且只有一个对象的场景，避免产生多个对象消耗过多的资源，或者某种类型的对象只应该有且只有一个。

UML类图：
单例模式几个关键点：

      、构造函数不对外开放，一般为private。

      、通过一个静态方法或者枚举返回单例类对象。

      、确保单例类的对象有且只有一个，尤其在多线程环境下。

      、确保单例类对象在反序列化时不会重新构建对象。


 *  */
public class AppStackManager {
    private Stack<Activity> mActivities = new Stack<>();
    private static AppStackManager appStackManager= null;
          //单列需要保证其他都是私有的 只有其中的获取实例这样就可以实现单列
    AppStackManager(){

    }
    public static AppStackManager getInstance(){
        if (appStackManager ==null){
            new AppStackManager();
        }
        return appStackManager;    //一种单列的模式写法 减少多次实例化；
    }

    public void addActivity(Activity activity){
        mActivities.add(activity);
    }

  //移除视图
    public void removeActivity(Activity activity){
        for (int i = mActivities.size()-1;i>=0;i--){
            //对于要移除或者删除的情况下都要考虑从最后开始往前面删除，防止空指针异常吧
            Activity activity1 = mActivities.get(i);
            if (activity1.getClass().equals(activity1.getClass())){
                activity1.finish();
                mActivities.remove(activity1);
                break;  //当找到这个activity就跳出循环  没意义；
            }
        }
         //第二种方法
        /*for (Activity temp:mActivities) {  //这种循环会每一次吧一个值给temp 但是不是数组进行判断而已
            if (activity.getClass().equals(temp.getClass())){
                temp.finish();
                mActivities.remove(temp);
                break; //
            }

        }*/
    }
     //移除当前的
    public void removeCurrent(Activity activity){
        Activity top = mActivities.lastElement();
        //当前的意味着是最后一个所以获取栈顶就相当与最后一个
                top.finish();
          mActivities.remove(top);
    }
    //移除所有
    public void removeAll(){
        for (int i = mActivities.size()-1;i>=0;i--){
            Activity activity1 = mActivities.get(i);
                activity1.finish();
                mActivities.remove(activity1);

            }
        }
        public int getStacksize(){
        return mActivities.size();
        }

}
