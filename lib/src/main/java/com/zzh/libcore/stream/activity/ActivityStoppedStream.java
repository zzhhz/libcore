package com.zzh.libcore.stream.activity;

import android.app.Activity;

import com.zzh.lib.stream.FStream;

public interface ActivityStoppedStream extends FStream {
    void onActivityStopped(Activity activity);
}
