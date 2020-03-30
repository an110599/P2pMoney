package com.ananhang.p2pmoney;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ananhang.p2pmoney.fragments.HomeFragment;
import com.ananhang.p2pmoney.fragments.MeFragment;
import com.ananhang.p2pmoney.fragments.MoreFragment;
import com.ananhang.p2pmoney.fragments.TouziFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity";
    @BindView(R.id.home_fg_image)
    ImageView homeFgImage;
    @BindView(R.id.home_fg_text)
    TextView homeFgText;
    @BindView(R.id.touzi_fg_image)
    ImageView touziFgImage;
    @BindView(R.id.touzi_fg_text)
    TextView touziFgText;
    @BindView(R.id.geren_fg_image)
    ImageView gerenFgImage;
    @BindView(R.id.geren_fg_text)
    TextView gerenFgText;
    @BindView(R.id.more_fg_image)
    ImageView moreFgImage;
    @BindView(R.id.more_fg_text)
    TextView moreFgText;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.ll_touzi)
    LinearLayout llTouzi;
    @BindView(R.id.ll_geren)
    LinearLayout llGeren;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    private HomeFragment mHomeFragment;
    private TouziFragment mTouziFragment;
    private MoreFragment mMoreFragment;
    private MeFragment mMeFragment;
    private FragmentTransaction mFragmentTransaction;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        llHome.performClick();
        //

    }
    @OnClick({R.id.ll_home,R.id.ll_touzi,R.id.ll_geren,R.id.ll_more})
    public void changgeTab(View view){  //这种相当于实现单击的接口，
        setImageTextSelect();
        switch(view.getId()){
            case R.id.ll_home:
                setSelect(0);
                Log.d(TAG,"changgeTab()----");
                break;
            case R.id.ll_touzi:
                setSelect(1);
                break;
            case R.id.ll_geren:
                setSelect(2);
                break;
            case R.id.ll_more:
                setSelect(3);
                break;

        }


    }
    private void setImageTextSelect() {  //全部设置没被选择这样可以切换效果
        Log.d(TAG,"setImageTextSelect()----");
        homeFgImage.setSelected(false);
        homeFgText.setSelected(false);
        touziFgImage.setSelected(false);
        touziFgText.setSelected(false);
        gerenFgImage.setSelected(false);
        gerenFgText.setSelected(false);
        moreFgImage.setSelected(false);
        moreFgText.setSelected(false);
    }
    private void setAsSelect(int i){
        if (i==0){
            homeFgImage.setSelected(true);
            homeFgText.setSelected(true);
        }
        else if (i==1){
            touziFgImage.setSelected(true);
            touziFgText.setSelected(true);
        }
        else if (i==2){
            gerenFgImage.setSelected(true);
            gerenFgText.setSelected(true);
        }
        else{
            moreFgImage.setSelected(true);
            moreFgText.setSelected(true);
        }

    }

    private void setSelect(int i) {
        setAsSelect(i); //用来设置选择的
        Log.d(TAG,"setAsSelect()----"+i);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = supportFragmentManager.beginTransaction();
        hindFragement(mFragmentTransaction);
        switch (i){
          case 0:
              if (mHomeFragment==null){
                  mHomeFragment = new HomeFragment();
                  mFragmentTransaction.add(R.id.import_content, mHomeFragment);
              }
              else mFragmentTransaction.show(mHomeFragment);     //因为add是添加在一个储存的容器中
                                           // 就保存在里面，如果里面有当需要就展示出来不需要重新实例化,节约时间和流量
             break;
            case 1:
                if (mTouziFragment==null) {
                    mTouziFragment = new TouziFragment();
                    mFragmentTransaction.add(R.id.import_content, mTouziFragment);
                }
                else mFragmentTransaction.show(mTouziFragment);
                break;
         case 2:
             if (mMeFragment==null) {
                 mMeFragment = new MeFragment();
                 mFragmentTransaction.add(R.id.import_content, mMeFragment);
             }
             else mFragmentTransaction.show(mMeFragment);
             break;
         case 3:
             if (mMoreFragment==null) {
                 mMoreFragment = new MoreFragment();
                 mFragmentTransaction.add(R.id.import_content, mMoreFragment);
             }
             else
                 mFragmentTransaction.show(mMoreFragment);
             break;
     }
        mFragmentTransaction.commit();
    }
    private void hindFragement(FragmentTransaction ft){
        if (mHomeFragment!=null){
            ft.hide(mHomeFragment);
        }
        if (mTouziFragment!=null){
            ft.hide(mTouziFragment);
        }
        if (mMeFragment!=null){
            ft.hide(mMeFragment);
        }
        if (mMoreFragment!=null){
            ft.hide(mMoreFragment);
        }
        //这个if和else if 之间是有联系的,当不满足if中的条件的时候,就会去执行else if ,如果if中的条件已经满足了,就不会去判断else if中的条件了

    }

}
