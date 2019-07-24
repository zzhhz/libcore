package com.zzh.libcore.utils;

/**
 * 通用的结果回调
 */
public interface FCommonCallback<T>
{
    void onSuccess(T result);

    void onError(int code, String desc);
}
