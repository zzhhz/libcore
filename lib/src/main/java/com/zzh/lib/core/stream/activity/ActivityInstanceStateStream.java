package com.zzh.lib.core.stream.activity;

import android.app.Activity;
import android.os.Bundle;

import com.zzh.lib.stream.FStream;


public interface ActivityInstanceStateStream extends FStream
{
    void onActivitySaveInstanceState(Activity activity, Bundle outState);

    void onActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState);
}
