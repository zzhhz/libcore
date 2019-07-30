package com.zzh.lib.core.app;

import android.app.Application;

import com.zzh.lib.core.HLibrary;
import com.zzh.lib.core.utils.LibCoreUtil;

public abstract class HApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        final String processName = LibCoreUtil.getProcessName(this);
        if (getPackageName().equals(processName)) {
            HLibrary.getInstance().init(this);
            onCreateMainProcess();
        }
    }

    /**
     * 主进程调用
     */
    protected abstract void onCreateMainProcess();
}
