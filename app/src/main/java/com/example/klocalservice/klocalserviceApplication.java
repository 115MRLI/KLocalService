package com.example.klocalservice;

import android.app.Application;

import com.example.klocalservice.manager.CoreServerManager;

public class klocalserviceApplication extends Application {
    //除非特殊情况，否则尽量少用
    private static klocalserviceApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //启动本地核心服务
        CoreServerManager.startCoreServer(this, 8080, 8081);
    }

    /**
     * 获取application实例
     *
     * @return
     */
    public static klocalserviceApplication get() {
        return application;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}