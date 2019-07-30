package com.zzh.lib.core.business;

import com.zzh.lib.stream.FStream;
import com.zzh.lib.core.business.stream.BSProgress;

import androidx.annotation.CallSuper;


public abstract class HBusiness
{
    private final String mTag;

    public HBusiness(String tag)
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
