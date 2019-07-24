package com.zzh.libcore.app;

import android.app.Application;

import com.zzh.libcore.FLibrary;
import com.zzh.libcore.utils.LibCoreUtil;

public abstract class FApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        final String processName = LibCoreUtil.getProcessName(this);
        if (getPackageName().equals(processName))
        {
            FLibrary.getInstance().init(this);
            onCreateMainProcess();
        }
    }

    /**
     * 主进程调用
     */
    protected abstract void onCreateMainProcess();
}
