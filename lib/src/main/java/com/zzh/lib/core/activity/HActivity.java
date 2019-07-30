package com.zzh.lib.core.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.zzh.lib.stream.FStream;
import com.zzh.lib.stream.FStreamManager;
import com.zzh.lib.core.stream.activity.ActivityCreatedStream;
import com.zzh.lib.core.stream.activity.ActivityDestroyedStream;
import com.zzh.lib.core.stream.activity.ActivityInstanceStateStream;
import com.zzh.lib.core.stream.activity.ActivityKeyEventStream;
import com.zzh.lib.core.stream.activity.ActivityPausedStream;
import com.zzh.lib.core.stream.activity.ActivityResultStream;
import com.zzh.lib.core.stream.activity.ActivityResumedStream;
import com.zzh.lib.core.stream.activity.ActivityStartedStream;
import com.zzh.lib.core.stream.activity.ActivityStoppedStream;
import com.zzh.lib.core.stream.activity.ActivityTouchEventStream;

import java.lang.reflect.Method;

import androidx.appcompat.app.AppCompatActivity;


public abstract class HActivity extends AppCompatActivity implements
        OnClickListener, FStream
{
    private ProgressDialog mProgressDialog;

    public Activity getActivity()
    {
        return this;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FStreamManager.getInstance().register(this);

        final int layoutId = onCreateContentView();
        if (layoutId != 0)
            setContentView(layoutId);

        notifyOnCreate(savedInstanceState);
    }

    /**
     * 返回activity布局id，基类调用的顺序：
     * <p>
     * 1. onCreateContentView()<br>
     * 2. setContentView()<br>
     * 3. onCreateTitleView() 或者 onCreateTitleViewLayoutId()<br>
     * 4. onInitTitleView(View view)<br>
     * 5. onInitContentView(View view)<br>
     *
     * @return
     */
    protected int onCreateContentView()
    {
        return 0;
    }

    @Override
    public void setContentView(int layoutId)
    {
        final View contentView = getLayoutInflater().inflate(layoutId, (ViewGroup) findViewById(android.R.id.content), false);
        setContentView(contentView);
    }

    @Override
    public void setContentView(View view)
    {
        final View contentView = addTitleViewIfNeed(view);
        contentView.setFitsSystemWindows(true);
        super.setContentView(contentView);

        onInitContentView(contentView);
    }

    /**
     * setContentView方法之后会回调此方法，可以用来初始化View
     *
     * @param view
     */
    protected void onInitContentView(View view)
    {

    }

    /**
     * 为contentView添加titleView
     *
     * @param contentView
     * @return
     */
    private View addTitleViewIfNeed(View contentView)
    {
        final View titleView = createTitleView();
        if (titleView == null)
            return contentView;

        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(titleView);
        linearLayout.addView(contentView);
        return linearLayout;
    }

    private View createTitleView()
    {
        View titleView = onCreateTitleView();
        if (titleView == null)
        {
            final int layoutId = onCreateTitleViewLayoutId();
            if (layoutId != 0)
                titleView = getLayoutInflater().inflate(layoutId, (ViewGroup) findViewById(android.R.id.content), false);
        }

        if (titleView != null)
        {
            titleView = transformTitleView(titleView);
            if (titleView == null)
                throw new RuntimeException("transformTitleView return null");

            onInitTitleView(titleView);
        }

        return titleView;
    }

    /**
     * 返回标题栏布局
     *
     * @return
     */
    protected View onCreateTitleView()
    {
        return null;
    }

    /**
     * 返回标题栏布局id
     *
     * @return
     */
    protected int onCreateTitleViewLayoutId()
    {
        return 0;
    }

    /**
     * 转换标题栏，可以做一些全局的修改
     *
     * @param view
     * @return
     */
    protected View transformTitleView(View view)
    {
        return view;
    }

    /**
     * 初始化标题栏view
     *
     * @param view
     */
    protected void onInitTitleView(View view)
    {

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        notifyOnStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        notifyOnResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        notifyOnPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        notifyOnStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        FStreamManager.getInstance().unregister(this);
        dismissProgressDialog();
        notifyOnDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        notifyOnSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        notifyOnRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        notifyOnActivityResult(requestCode, resultCode, data);
    }

    private ActivityTouchEventStream mTouchEventStream;

    private ActivityTouchEventStream getTouchEventStream()
    {
        if (mTouchEventStream == null)
        {
            mTouchEventStream = new FStream.ProxyBuilder()
                    .setTag(getStreamTag())
                    .setDispatchCallback(new DispatchCallback()
                    {
                        @Override
                        public boolean beforeDispatch(FStream stream, Method method, Object[] methodParams)
                        {
                            return false;
                        }

                        @Override
                        public boolean afterDispatch(FStream stream, Method method, Object[] methodParams, Object methodResult)
                        {
                            if (Boolean.TRUE.equals(methodResult))
                                return true;

                            return false;
                        }
                    })
                    .build(ActivityTouchEventStream.class);
        }
        return mTouchEventStream;
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev)
    {
        final boolean result = getTouchEventStream().dispatchTouchEvent(HActivity.this, ev);
        if (result)
            return true;

        return super.dispatchTouchEvent(ev);
    }

    private ActivityKeyEventStream mKeyEventStream;

    private ActivityKeyEventStream getKeyEventStream()
    {
        if (mKeyEventStream == null)
        {
            mKeyEventStream = new FStream.ProxyBuilder()
                    .setTag(getStreamTag())
                    .setDispatchCallback(new DispatchCallback()
                    {
                        @Override
                        public boolean beforeDispatch(FStream stream, Method method, Object[] methodParams)
                        {
                            return false;
                        }

                        @Override
                        public boolean afterDispatch(FStream stream, Method method, Object[] methodParams, Object methodResult)
                        {
                            if (Boolean.TRUE.equals(methodResult))
                                return true;

                            return false;
                        }
                    })
                    .build(ActivityKeyEventStream.class);
        }
        return mKeyEventStream;
    }

    @Override
    public boolean dispatchKeyEvent(final KeyEvent event)
    {
        final boolean result = getKeyEventStream().dispatchKeyEvent(HActivity.this, event);
        if (result)
            return true;

        return super.dispatchKeyEvent(event);
    }

    /**
     * activity是否处于竖屏方向
     *
     * @return
     */
    public boolean isOrientationPortrait()
    {
        return Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation;
    }

    /**
     * activity是否处于横屏方向
     *
     * @return
     */
    public boolean isOrientationLandscape()
    {
        return Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation;
    }

    /**
     * 设置activity为竖屏
     */
    public void setOrientationPortrait()
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 设置activity为横屏
     */
    public void setOrientationLandscape()
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void showProgressDialog(String msg)
    {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(this);

        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    public void dismissProgressDialog()
    {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

    /**
     * 设置activity是否全屏
     *
     * @param fullScreen true-全屏，false-不全屏
     */
    public void setFullScreen(boolean fullScreen)
    {
        if (fullScreen)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    //------------notify callback start------------------

    @Override
    public Object getTagForStream(Class<? extends FStream> clazz)
    {
        return getStreamTag();
    }

    public final String getStreamTag()
    {
        return HActivity.this.toString();
    }

    private void notifyOnCreate(final Bundle savedInstanceState)
    {
        final ActivityCreatedStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityCreatedStream.class);

        stream.onActivityCreated(HActivity.this, savedInstanceState);
    }

    private void notifyOnStart()
    {
        final ActivityStartedStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityStartedStream.class);

        stream.onActivityStarted(HActivity.this);
    }

    private void notifyOnResume()
    {
        final ActivityResumedStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityResumedStream.class);

        stream.onActivityResumed(HActivity.this);
    }

    private void notifyOnPause()
    {
        final ActivityPausedStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityPausedStream.class);

        stream.onActivityPaused(HActivity.this);
    }

    private void notifyOnStop()
    {
        final ActivityStoppedStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityStoppedStream.class);

        stream.onActivityStopped(HActivity.this);
    }

    private void notifyOnDestroy()
    {
        final ActivityDestroyedStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityDestroyedStream.class);

        stream.onActivityDestroyed(HActivity.this);
    }

    private void notifyOnSaveInstanceState(final Bundle outState)
    {
        final ActivityInstanceStateStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityInstanceStateStream.class);

        stream.onActivitySaveInstanceState(HActivity.this, outState);
    }

    private void notifyOnRestoreInstanceState(final Bundle savedInstanceState)
    {
        final ActivityInstanceStateStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityInstanceStateStream.class);

        stream.onActivityRestoreInstanceState(HActivity.this, savedInstanceState);
    }

    private void notifyOnActivityResult(final int requestCode, final int resultCode, final Intent data)
    {
        final ActivityResultStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityResultStream.class);

        stream.onActivityResult(HActivity.this, requestCode, resultCode, data);
    }

    //------------notify callback end------------------

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params)
    {
        if (params == null)
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        super.addContentView(view, params);
    }

    @Override
    public void onClick(View v)
    {

    }
}
