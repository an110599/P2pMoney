package com.ananhang.p2pmoney.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;

import com.ananhang.p2pmoney.MainActivity;
import com.ananhang.p2pmoney.R;
import com.ananhang.p2pmoney.common.AppStackManager;
import com.ananhang.p2pmoney.common.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.setting_img)
    ImageView settingImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,fragment_view);
        initTitle();
        return fragment_view;
    }

    private void initTitle() {
        if (back!=null&&settingImg!=null) {
            back.setVisibility(View.INVISIBLE);  //设置为不可见同时不占用空间
            settingImg.setVisibility(View.INVISIBLE);
            topTitle.setText("首页");
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
