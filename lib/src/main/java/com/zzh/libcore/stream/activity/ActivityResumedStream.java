package com.zzh.libcore.stream.activity;

import android.app.Activity;

import com.zzh.lib.stream.FStream;

public interface ActivityResumedStream extends FStream {
    void onActivityResumed(Activity activity);
}
