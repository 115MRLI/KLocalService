package com.example.klocalservice;

import android.app.Application;

public class klocalserviceApplication extends Application {
    //除非特殊情况，否则尽量少用
    private static klocalserviceApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
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