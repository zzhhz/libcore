package com.zzh.lib.core.stream.activity;

import android.app.Activity;

import com.zzh.lib.stream.FStream;

public interface ActivityPausedStream extends FStream {
    void onActivityPaused(Activity activity);
}
