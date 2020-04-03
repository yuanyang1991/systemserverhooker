package com.yuanyang.lib;

import android.util.Log;

/**
 * Created by tangyinsheng on 2017/7/31.
 */

public abstract class Interceptor<T_TARGET> {
    private static final String TAG = "Magician.Interceptor";

    private T_TARGET mTarget = null;
    private volatile boolean mInstalled = false;

    protected abstract T_TARGET fetchTarget() throws Throwable;

    protected T_TARGET decorate(T_TARGET target) throws Throwable {
        return target;
    }

    protected abstract void inject(T_TARGET decorated) throws Throwable;

    public synchronized void install() throws Throwable {
        try {
            final T_TARGET target = fetchTarget();
            final T_TARGET decorated = decorate(target);
            if (decorated != target) {
                if (mTarget == null) {
                    mTarget = target;
                }
                inject(decorated);
            } else {
                Log.w(TAG, "target: " + target + " was already hooked.");
            }
            mInstalled = true;
        } catch (Throwable thr) {
            mTarget = null;
            throw new Throwable(thr);
        }
    }

    /**
     * 说明：解决AMS和PMS hook点卸载失败问题，
     * 增加dex卸载保证应用正常启动。
     * <br>修改人：huyang
     * <br>修改时间：2018/11/2 15:26
     */
    public synchronized void uninstall() throws Throwable {
        if (mInstalled) {
            try {
                inject(mTarget);
                mTarget = null;
                mInstalled = false;
            } catch (Throwable thr) {
                throw new Throwable(thr);
            }
        }
    }

    protected interface ITinkerHotplugProxy {
        // Marker interface for proxy objects created by tinker.
    }
}
