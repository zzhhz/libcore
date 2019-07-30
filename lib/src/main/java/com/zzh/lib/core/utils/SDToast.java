package com.zzh.lib.core.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.zzh.lib.core.HLibrary;


@Deprecated
public class SDToast
{
    public static final Handler S_HANDLER = new Handler(Looper.getMainLooper());

    private static Toast sToast;

    public static void showToast(CharSequence text)
    {
        showToast(text, Toast.LENGTH_LONG);
    }

    public static void showToast(final CharSequence text, final int duration)
    {
        if (Looper.myLooper() == Looper.getMainLooper())
        {
            showInternal(text, duration);
        } else
        {
            S_HANDLER.post(new Runnable()
            {
                @Override
                public void run()
                {
                    showInternal(text, duration);
                }
            });
        }
    }

    private static void showInternal(CharSequence text, int duration)
    {
        if (TextUtils.isEmpty(text))
        {
            return;
        }
        if (sToast != null)
        {
            sToast.setText(text);
            sToast.setDuration(duration);
        } else
        {
            sToast = Toast.makeText(HLibrary.getInstance().getContext(), text, duration);
        }
        sToast.show();
    }

}
