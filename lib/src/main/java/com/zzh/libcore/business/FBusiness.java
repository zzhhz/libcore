package com.zzh.libcore.business;

import com.zzh.lib.stream.FStream;
import com.zzh.libcore.business.stream.BSProgress;

import androidx.annotation.CallSuper;


public abstract class FBusiness
{
    private final String mTag;

    public FBusiness(String tag)
    {
        mTag = tag;
    }

    public final String getTag()
    {
        return mTag;
    }

    protected final <T extends FStream> T getStream(Class<T> clazz)
    {
        return new FStream.ProxyBuilder().setTag(getTag()).build(clazz);
    }

    protected final BSProgress getProgress()
    {
        return getStream(BSProgress.class);
    }

    @CallSuper
    public void onDestroy()
    {
    }
}
