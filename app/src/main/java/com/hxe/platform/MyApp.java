package com.hxe.platform;

import android.app.Application;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.hxe.platform.service.DemoIntentService;
import com.hxe.platform.service.DemoPushService;
import com.igexin.sdk.PushManager;
import com.mob.MobSDK;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * Created by 张肖肖 on 2017/11/15.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MobSDK.init(this, "227ba98c2c954", "55568288eda7dda06e26a8c709fc50f4");

        AutoLayoutConifg.getInstance().useDeviceSize();

        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(),DemoPushService.class);

        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(),DemoIntentService.class);

        Fresco.initialize(this, ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(this)
                                .build()
                )
                .build()
        );

    }
}
