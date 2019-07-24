package com.zzh.libcore.stream.activity;

import android.app.Activity;
import android.view.MotionEvent;

import com.zzh.lib.stream.FStream;

public interface ActivityTouchEventStream extends FStream {
    boolean dispatchTouchEvent(Activity activity, MotionEvent event);
}
