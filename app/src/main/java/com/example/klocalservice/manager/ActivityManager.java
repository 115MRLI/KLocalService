package com.example.klocalservice.manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * activity管理器
 */
public class ActivityManager {
    private List<Activity> activityList = new ArrayList<>();
    private static ActivityManager activityManager;

    private ActivityManager() {
    }

    /**
     * 获取单利
     *
     * @return
     */
    public static ActivityManager get() {
        if (null == activityManager) {
            activityManager = new ActivityManager();
        }
        return activityManager;
    }

    /**
     * 添加
     *
     * @param activity
     */
    public void add(Activity activity) {
        if (null != activityList && !activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    /**
     * 移除
     *
     * @param activity
     */
    public void remove(Activity activity) {
        if (null != activityList && activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    /**
     * 移除所有
     */
    public void removeAll() {
        if (null != activityList) {
            for (Activity activity : activityList) {
                activity.finish();
            }
        }
    }
}