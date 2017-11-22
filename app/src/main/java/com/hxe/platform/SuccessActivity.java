package com.hxe.platform;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class SuccessActivity extends AutoLayoutActivity implements View.OnClickListener {

    private ImageView success_login;
    private SharedPreferences success;
    private boolean two;
    private EditText success_et_mobile;
    private EditText success_et_yanzheng;
    private ImageView success_iv_yanzheng;
    private TextView success_cg;
    private ImageView success_yanzheng;
    private EventHandler eventHandler;
    private TextView success_tv_s;

    private int timess = 30;

    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        ActionBar action= getSupportActionBar();
        action.hide();  //登录页面

        //登录按钮
        success_login = findViewById(R.id.success_login);
        //手机输入框
        success_et_mobile = findViewById(R.id.success_et_mobile);
        //短信输入框
        success_et_yanzheng = findViewById(R.id.success_et_yanzheng);
        //获取验证码按钮
        success_iv_yanzheng = findViewById(R.id.success_iv_yanzheng);
        //切换常规登录方式
        success_cg = findViewById(R.id.success_cg);
        //短信验证的图标
        success_yanzheng = findViewById(R.id.success_yanzheng);
        //30s
        success_tv_s = findViewById(R.id.success_tv_s);


        //判断是否是第一次进入
        success = getSharedPreferences("success", MODE_PRIVATE);
        two = success.getBoolean("two", true);
        if(two){
            success_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();//自己创建的方法
                //点击进入登录界面
                //第一次跳转到登录页面
                success.edit().putBoolean("two",false).commit();

                SMSSDK.submitVerificationCode("86", success_et_mobile.getText().toString(), success_et_yanzheng.getText().toString());

                //点击跳转主页面
                Intent intent = new Intent(SuccessActivity.this,ZhuActivity.class);
                startActivity(intent);
                finish();

            }
        });

        }else {
            //点击进入登录界面
            Intent intent = new Intent(SuccessActivity.this,ZhuActivity.class);
            startActivity(intent);
            finish();
        }

        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SuccessActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证码成功
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SuccessActivity.this, " 提交验证码成功", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                            //获取验证码成功
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SuccessActivity.this, " 获取验证码成功", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                            //返回支持发送验证码的国家列表
                        }
                    }else{
                        ((Throwable)data).printStackTrace();
                    }
                }
            }

        };
        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);

            //点击事件总和
        //点击验证码的按钮
        success_iv_yanzheng.setOnClickListener(this);
        //点击切换常规登录方式
        success_cg.setOnClickListener(this);

        //30s倒计时
        timer.schedule(task,1000,1000);

    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    timess--;
                    success_tv_s.setText(""+timess+"s");
                    if(timess < 0){
                        timer.cancel();
                        success_tv_s.setVisibility(View.GONE);
                        //重新获取图片
                        success_iv_yanzheng.setImageResource(R.mipmap.cxhq);
                    }
                }
            });
        }
    };

    //在onDestroy中注销sdk
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
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

    int flag = 0;
    int flag2 = 0;
    int flag3 = 0;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.success_iv_yanzheng:
                //点击验证码的按钮
                if (TextUtils.isEmpty(success_et_mobile.getText().toString())) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                SMSSDK.getVerificationCode("86", success_et_mobile.getText().toString());
                //点击短信的验证码
               // success_iv_yanzheng.setImageResource(R.mipmap.yanzhengma);
                if(flag2 == 0){

                    success_iv_yanzheng.setImageResource(R.mipmap.times);
                    success_tv_s.setVisibility(View.VISIBLE);
                    flag2 = 1;
                }else if(flag2 == 1){

                    success_iv_yanzheng.setImageResource(R.mipmap.yanzhengma);
                    success_tv_s.setVisibility(View.GONE);
                    flag2 = 0;
                }

                break;
            case R.id.success_cg:
                //点击切换常规登录方式
                if(flag == 0) {
                      success_et_yanzheng.setText("输入登录密码");
                    success_et_yanzheng.setTextColor(Color.parseColor("#999999"));
                    success_cg.setText("短信验证码登录方式");
                    success_yanzheng.setImageResource(R.drawable.pwd);
                    success_iv_yanzheng.setImageResource(R.drawable.showpwd);

                    success_iv_yanzheng.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           //点击睁眼闭眼的
                            if(flag3 == 0){
                                success_iv_yanzheng.setImageResource(R.mipmap.yincang);
                                flag3 = 1;
                            }else if(flag3 == 1){
                                success_iv_yanzheng.setImageResource(R.drawable.showpwd);
                                flag3 = 0;
                            }
                        }
                    });

                    flag = 1;
                }else if(flag == 1){
                       success_et_yanzheng.setText("输入短信验证码");
                    success_et_yanzheng.setTextColor(Color.parseColor("#999999"));
                    success_cg.setText("使用常规登录方式");

                    success_yanzheng.setImageResource(R.mipmap.et_yanzheng);
                    success_iv_yanzheng.setImageResource(R.mipmap.yanzhengma);
                    success_iv_yanzheng.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                                //点击短信的验证码
                                if (flag2 == 0) {
                                    success_iv_yanzheng.setImageResource(R.mipmap.times);
                                    flag2 = 1;
                                } else if (flag2 == 1) {
                                    success_iv_yanzheng.setImageResource(R.mipmap.yanzhengma);
                                    flag2 = 0;
                                }

                        }
                    });

                    flag = 0;

                }
                break;
        }

    }

    //自己建立的方法
    private void verify() {
        if (TextUtils.isEmpty(success_et_mobile.getText().toString())) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(success_et_yanzheng.getText().toString())) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }



    }


}
