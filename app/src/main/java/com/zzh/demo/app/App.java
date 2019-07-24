package com.zzh.demo.app;


import com.zzh.libcore.app.FApplication;

/**
 * Created by Administrator on 2017/5/27.
 */
public class App extends FApplication
{
    private static App sInstance;

    public static App getInstance()
    {
        return sInstance;
    }

    @Override
    protected void onCreateMainProcess()
    {
        sInstance = this;
    }
}
