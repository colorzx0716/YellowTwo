package com.hxe.platform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.AutoLayoutActivity;

public class WelcomeActivity extends AutoLayoutActivity {

    private ImageView welcome_img2;
    private ImageView welcome_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //欢迎页面
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //动图
        welcome_img = findViewById(R.id.welcome_img);

        Glide.with(getApplicationContext()).load(R.mipmap.shanpingdonghua)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(welcome_img, 1));

        welcome_img2 = findViewById(R.id.welcome_img2);

        //点击欢迎图片，跳转到登录页面
        welcome_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击进入登录界面
                Intent intent = new Intent(WelcomeActivity.this,SuccessActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
