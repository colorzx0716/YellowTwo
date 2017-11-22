package com.hxe.platform;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hxe.platform.adapter.FragAdapter;
import com.hxe.platform.fragment.Fragment1;
import com.hxe.platform.fragment.Fragment2;
import com.hxe.platform.fragment.Fragment3;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

public class ZhuActivity extends AutoLayoutActivity implements View.OnClickListener {
   // private SharedPreferences success;
    private ViewPager zhu_vp;
    private ImageView zhu_yuyue;
    private ImageView zhu_bott;
    private ImageView zhu_wo;
    private FrameLayout zhu_fl;
    // private Button btn_quxiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu);

        //viewpager的ID
        zhu_vp = findViewById(R.id.zhu_vp);
        //预约的按钮
        zhu_yuyue = findViewById(R.id.zhu_yuyue);
        //扫码的按钮
        zhu_bott = findViewById(R.id.zhu_bott);
        //我的按钮
        zhu_wo = findViewById(R.id.zhu_wo);

        //隐藏的Fragment,默认是隐藏起来，点击bott才能显示
        zhu_fl = findViewById(R.id.zhu_fl);


        //点击总事件
        zhu_yuyue.setOnClickListener(this);
        zhu_bott.setOnClickListener(this);
        zhu_wo.setOnClickListener(this);


        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new Fragment1());
        fragmentList.add(new Fragment3());

        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(),this,fragmentList);
        zhu_vp.setAdapter(adapter);
        zhu_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(position == 0){
                    zhu_yuyue.setImageResource(R.drawable.yuyue2);
                    zhu_wo.setImageResource(R.drawable.wo1);
                }

                if(position == 1){
                    zhu_yuyue.setImageResource(R.drawable.yuyue);
                    zhu_wo.setImageResource(R.drawable.wo2);
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //默认是显示第一页
        getSupportFragmentManager().beginTransaction().replace(R.id.zhu_fl,new Fragment2()).commit();
       /* zhu_vp.setCurrentItem(0);
        zhu_yuyue.setImageResource(R.drawable.yuyue2);*/


       /* btn_quxiao = findViewById(R.id.btn_quxiao);

        btn_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //清除SharedPreferences
                success = getSharedPreferences("success", MODE_PRIVATE);
                SharedPreferences.Editor edit = success.edit();
                edit.clear();
                edit.commit();

            }
        });*/

    }




    //这两个方法要在每个Activity里都写上
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    //点击总事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.zhu_yuyue:
                //预约
                zhu_fl.setVisibility(View.GONE);
                zhu_vp.setVisibility(View.VISIBLE);
                zhu_yuyue.setImageResource(R.drawable.yuyue2);
                zhu_wo.setImageResource(R.drawable.wo1);
                zhu_vp.setCurrentItem(0);
               // getSupportFragmentManager().beginTransaction().replace(R.id.zhu_fl,new Fragment1()).commit();
                break;
            case R.id.zhu_wo:
                //我的
                zhu_fl.setVisibility(View.GONE);
                zhu_vp.setVisibility(View.VISIBLE);
                zhu_yuyue.setImageResource(R.drawable.yuyue);
                zhu_wo.setImageResource(R.drawable.wo2);
                zhu_vp.setCurrentItem(1);
              //  getSupportFragmentManager().beginTransaction().replace(R.id.zhu_fl,new Fragment3()).commit();

                break;
            case R.id.zhu_bott:
                //扫码
                zhu_fl.setVisibility(View.VISIBLE);
                zhu_vp.setVisibility(View.GONE);
                zhu_yuyue.setImageResource(R.drawable.yuyue);
                zhu_wo.setImageResource(R.drawable.wo1);

                getSupportFragmentManager().beginTransaction().replace(R.id.zhu_fl,new Fragment2()).commit();

                break;
        }

    }
}
