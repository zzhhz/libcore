package com.zzh.lib.core.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import java.lang.reflect.Field;

import androidx.appcompat.widget.AppCompatRatingBar;

public class HRatingBar extends AppCompatRatingBar
{

	public HRatingBar(Context context)
	{
		super(context);
	}

	public HRatingBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public HRatingBar(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		try
		{
			Class<?> clazz = Class.forName("android.widget.ProgressBar");
			Field field = clazz.getDeclaredField("mSampleTile");
			field.setAccessible(true);
			Object bitmapObject = field.get(this);
			Bitmap bitmap = (Bitmap) bitmapObject;
			int height = bitmap.getHeight();
			if (height > 0)
			{
				setMeasuredDimension(getMeasuredWidth(), resolveSizeAndState(height, heightMeasureSpec, 0));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
