package com.zzh.libcore.stream.activity;

import android.app.Activity;

import com.zzh.lib.stream.FStream;

public interface ActivityDestroyedStream extends FStream {
    void onActivityDestroyed(Activity activity);
}
