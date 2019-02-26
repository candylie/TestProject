package com.zk.framework.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * -
 *
 * @author 张科
 * @date 2019/2/26.
 */
public class ZApp extends Application {

    @SuppressLint("StaticFieldLeak")
    private static ActivityLifecycleCallbacks callbacks;

    @Override
    public void onCreate() {
        super.onCreate();
        callbacks = new ActivityLifecycleCallbacks();

        registerActivityLifecycleCallbacks(callbacks);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        callbacks.relese();
        callbacks = null;
    }


    public static Activity getNowActivity() {
        return callbacks.getNowActivity();
    }

    private static class ActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

        private Activity nowActivity;


        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            nowActivity = activity;
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }

        Activity getNowActivity() {
            return nowActivity;
        }

        void relese() {
            nowActivity = null;
        }
    }

}
