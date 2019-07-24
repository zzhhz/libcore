package com.zzh.libcore.business.stream;


import com.zzh.lib.stream.FStream;

public interface BSProgress extends FStream
{
    void bsShowProgress(String msg);

    void bsHideProgress();
}
