package com.ananhang.p2pmoney.utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    private View mChildAt; //子控件
    private float mY;
    private int mLastmovedistance;
   private Rect recordAfter = new Rect();  //矩形 的上左下右 来装入一开始的子控件的上左下右的距离
    private boolean mAnnimationFinish = true;  //用来做开关

    public MyScrollView(Context context) {
        super(context);
    }
                                        //属性集
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
                                                                //定义样式属性；
    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {  //完成xml之后调用
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount>0){
            mChildAt = getChildAt(0); //要操作的子布局
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mChildAt==null){
            return super.onTouchEvent(ev);
        }
        else {
             //如果不为空 就写自己想要的触摸
            myTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
        }
        /*
        *自定义触摸事件
        * */
    private void myTouchEvent(MotionEvent ev) {  //motion 运动事件
   if (mAnnimationFinish) {
       int evAction = ev.getAction(); //获取触摸的动作
       switch (evAction) {
           case MotionEvent.ACTION_DOWN:  //按下
               //记录按下时候的纵坐标；
               mY = ev.getY();
               Log.e("myTouchEvent", "按下的y坐标" + mY);
               break;
           case MotionEvent.ACTION_MOVE:  //移动
               float move = ev.getY();  //获取到移动的最后的y 这个值一直在变化 但是最终要形成一个值
               mLastmovedistance = (int) (move-mY); // 移动最后的一个值也就后续要抬起的值一定会大于一开始按下的值，故减去之前按下的Y就得出了移动的距离
               if (recordAfter.isEmpty()) {     //一开始为空就要进行赋值 相当于得到一开始子控件的上左下右让后面复原
                   recordAfter.set(mChildAt.getLeft(), mChildAt.getTop(), mChildAt.getRight(), mChildAt.getBottom());
               }
               if (isNeedMove()) { //开关方法 如果成立则进行对子控件的整体移动变化，否则不进行，
                   mChildAt.layout(mChildAt.getLeft(), mChildAt.getTop() + mLastmovedistance / 2, mChildAt.getRight(), mChildAt.getBottom() + mLastmovedistance / 2);
               }
               Log.e("myTouchEvent", "按下的move坐标" + move + "最终移动了的距离" + mLastmovedistance);
               break;
           case MotionEvent.ACTION_UP:     //抬起
               if (isNeedAnnimation()) {   //抬起就要进行复原子控件 其中加入动画
                   myannimation();
               }
               break;

       }

   }
    }

    private void myannimation() {   //这是一个平移动画的方法
        //创建平移动画 只对最后一个到Y的目的距离              比如一开始的为0，后面在获取新的子控件的top坐标Y=100，所以得出为-100（回到原来的0)然后原本100然后再回到-100就为0
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0,  recordAfter.top-mChildAt.getTop());//
        animation.setDuration(200); //设置延迟
        animation.setAnimationListener(new Animation.AnimationListener() { //对动画进行监听==onclickli...
            @Override
            public void onAnimationStart(Animation animation) {
                //当动画开始的时候就不许去滑动它 设置开关为false
                mAnnimationFinish = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //当动画结束的时候清理动画，然后恢复原来子控件的原始位置，避免有差错，最后清理Rect中的数据，然后吧触摸移动开关打开 就又可以滑动了
                mChildAt.clearAnimation();
                mChildAt.layout(recordAfter.left,recordAfter.top,recordAfter.right,recordAfter.bottom);
                recordAfter.setEmpty();
                mAnnimationFinish = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mChildAt.setAnimation(animation);   //对控件进行设置动画（开启动画）
    }

    private boolean isNeedAnnimation() {

        return !recordAfter.isEmpty();
    }

    private Boolean isNeedMove() {  //判断到底到顶的方法开关

        //先获取子控件的实际高度（本身不随屏幕变化而变化）
        int measuredHeight = mChildAt.getMeasuredHeight();
        // 再获取屏幕能显示的高度，手机不同显示的高度不同
        int screenHeight = getHeight();

        //然后在用子控件的实际高度减去屏幕的整体能显示的高度，就是他们之间的偏移量
        int offsetHeight = measuredHeight - screenHeight;
        //一般用子控件高度-屏幕高度，如果子控件没超过屏幕（没造成需要滚动的效果，都是负值）；
        //超过了就是他们之间差值的偏移量，这个量代表着可以继续往下移动多少 ==偏移量

        //这是可以滚动的Y 如果子控件的实际高度<屏幕能显示的高度，就代表滚动的y只能为0；
        //其实点为0，往下是正值，没有负值。如果子控件的实际高度>屏幕高度，往下滚动的距离就是偏移量（子控件高度-屏幕高度）
        int scrollY = getScrollY();
        Log.e("isNeedMove","滚动"+scrollY+"屏幕高度"+ screenHeight+"偏移量"+offsetHeight);

        /*
           因为到为0时是往下拉，拉到顶了所以能拉的距离为0，不再减小时
           当等于偏移量时，是往上拉，拉到底之后滚动最终上面的部分就是偏移量，不再增加时；
           所以两个临界点0或子控件实际高度-屏幕高度的值（偏移量）代表着到顶到底；
        */

        if (scrollY ==0 || scrollY == offsetHeight){
            return true;
        }
        return false;
    }

}
