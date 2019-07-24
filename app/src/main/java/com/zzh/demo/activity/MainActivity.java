package com.zzh.demo.activity;

import android.os.Bundle;

import com.zzh.demo.R;
import com.zzh.libcore.activity.FActivity;

public class MainActivity extends FActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
    }
}
