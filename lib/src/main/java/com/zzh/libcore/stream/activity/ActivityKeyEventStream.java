package com.zzh.libcore.stream.activity;

import android.app.Activity;
import android.view.KeyEvent;

import com.zzh.lib.stream.FStream;

public interface ActivityKeyEventStream extends FStream {
    boolean dispatchKeyEvent(Activity activity, KeyEvent event);
}
