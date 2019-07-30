package com.zzh.lib.core.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import com.zzh.lib.stream.FStream;
import com.zzh.lib.stream.FStreamManager;
import com.zzh.lib.core.activity.HActivity;
import com.zzh.lib.core.stream.activity.ActivityDestroyedStream;
import com.zzh.libcore.view.HViewGroup;

/**
 * 如果手动的new对象的话Context必须传入Activity对象
 */
public class HControlView extends HViewGroup implements ActivityDestroyedStream {
    public HControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        FStreamManager.getInstance().bindView(this, this);
    }

    public HActivity getFActivity() {
        final Activity activity = getActivity();
        return activity instanceof HActivity ? (HActivity) activity : null;
    }

    @Override
    public Object getTagForStream(Class<? extends FStream> clazz) {
        return getStreamTagActivity();
    }

    public final String getStreamTagActivity() {
        final HActivity fActivity = getFActivity();
        if (fActivity != null)
            return fActivity.getStreamTag();

        final Activity activity = getActivity();
        if (activity != null)
            return activity.toString();

        return getStreamTagView();
    }

    public final String getStreamTagView() {
        final String className = getClass().getName();
        final String hashCode = Integer.toHexString(System.identityHashCode(this));
        return className + "@" + hashCode;
    }

    public void showProgressDialog(String msg) {
        final HActivity fActivity = getFActivity();
        if (fActivity != null)
            fActivity.showProgressDialog(msg);
    }

    public void dismissProgressDialog() {
        final HActivity fActivity = getFActivity();
        if (fActivity != null)
            fActivity.dismissProgressDialog();
    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
