package com.hxe.platform;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private View view1;
    private View view2;
    private View view3;
    private View view4;

    private List<View> viewList;
    private ImageView four_iv,four_iv2;
    private SharedPreferences com;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar action= getSupportActionBar();
        action.hide();

        //ViewPager滑动页面

        vp = findViewById(R.id.vp);

        com = getSharedPreferences("com", MODE_PRIVATE);
        boolean first = com.getBoolean("first", true);
        if(first){
            //第一次跳转到登录页面
            com.edit().putBoolean("first",false).commit();

        }else{

            //第二次直接跳转到欢迎页面
            Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
            startActivity(intent);
            finish();

        }

        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.one_item, null);
        view2 = inflater.inflate(R.layout.two_item, null);
        view3 = inflater.inflate(R.layout.third_item, null);
        view4 = inflater.inflate(R.layout.four_item, null);

        four_iv = view4.findViewById(R.id.four_iv);
        four_iv2 = view4.findViewById(R.id.four_iv2);

        Glide.with(getApplicationContext()).load(R.mipmap.shanpingdonghua)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(four_iv2, 1));

        //点击跳转到登录页面
        four_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,SuccessActivity.class);
                startActivity(intent);
                finish();

            }
        });

        viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);

        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                container.addView(viewList.get(position));

                return viewList.get(position);

            }
        };

        vp.setAdapter(pagerAdapter);

        //onCreate方法结尾
    }


    //沉浸式
    //全屏
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus&& Build.VERSION.SDK_INT>=19){
            View decorView=getWindow().getDecorView();
            int option=View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(option);
        }
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

}
