package com.zzh.lib.core.stream.activity;

import android.app.Activity;
import android.os.Bundle;

import com.zzh.lib.stream.FStream;

public interface ActivityCreatedStream extends FStream {
    void onActivityCreated(Activity activity, Bundle savedInstanceState);
}
