package com.zzh.demo.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.zzh.libcore.stream.activity.ActivityDestroyedStream;
import com.zzh.libcore.stream.activity.ActivityResumedStream;
import com.zzh.libcore.stream.activity.ActivityStoppedStream;
import com.zzh.libcore.view.FControlView;

public class TestAppView extends FControlView implements ActivityResumedStream, ActivityStoppedStream, ActivityDestroyedStream
{
    public static final String TAG = TestAppView.class.getSimpleName();

    public TestAppView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        Log.i(TAG, "onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        Log.i(TAG, "onDetachedFromWindow");
    }

    @Override
    public void onActivityResumed(Activity activity)
    {
        Log.i(TAG, "onActivityResumed:" + activity);
    }

    @Override
    public void onActivityStopped(Activity activity)
    {
        Log.i(TAG, "onActivityStopped:" + activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {
        Log.i(TAG, "onActivityDestroyed:" + activity);
    }
}
