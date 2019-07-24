package com.zzh.libcore.stream.activity;

import android.app.Activity;

import com.zzh.lib.stream.FStream;


public interface ActivityStartedStream extends FStream {
    void onActivityStarted(Activity activity);
}
