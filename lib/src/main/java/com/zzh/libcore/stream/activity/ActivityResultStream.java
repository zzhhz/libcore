package com.zzh.libcore.stream.activity;

import android.app.Activity;
import android.content.Intent;

import com.zzh.lib.stream.FStream;


public interface ActivityResultStream extends FStream {
    void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data);
}
