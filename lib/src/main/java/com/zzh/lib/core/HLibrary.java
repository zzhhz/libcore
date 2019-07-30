package com.zzh.lib.core;

import android.app.Application;
import android.content.Context;

import com.zzh.lib.core.utils.HActivityStack;
import com.zzh.lib.core.utils.HAppBackgroundListener;

public class HLibrary {
    private static HLibrary sInstance;
    private Context mContext;

    private HLibrary() {
    }

    public static HLibrary getInstance() {
        if (sInstance == null) {
            synchronized (HLibrary.class) {
                if (sInstance == null)
                    sInstance = new HLibrary();
            }
        }
        return sInstance;
    }

    public Context getContext() {
        return mContext;
    }

    public synchronized void init(Application application) {
        if (mContext == null) {
            mContext = application;
            HActivityStack.getInstance().init(application);
            HAppBackgroundListener.getInstance().init(application);
        }
    }
}
