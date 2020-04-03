package com.yuanyang.systemserverhooker;

import android.app.Application;
import android.content.Context;

import com.yuanyang.lib.ServiceBinderInterceptor;

/**
 * 说明：
 * 作者：yuany
 * 时间：2020/4/3 15:51
 */
public class MyApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ServiceBinderInterceptor tmInterceptor = new ServiceBinderInterceptor(this, "phone", new TeleHandler());
        try {
            tmInterceptor.install();
        } catch (Throwable throwable) {
            try {
                tmInterceptor.uninstall();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

    }
}
