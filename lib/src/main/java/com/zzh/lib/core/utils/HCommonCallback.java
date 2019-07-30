package com.zzh.lib.core.utils;

/**
 * 通用的结果回调
 */
public interface HCommonCallback<T>
{
    void onSuccess(T result);

    void onError(int code, String desc);
}
