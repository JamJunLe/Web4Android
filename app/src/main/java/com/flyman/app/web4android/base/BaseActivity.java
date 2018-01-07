package com.flyman.app.web4android.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.flyman.app.web4android.R;
import com.jaeger.library.StatusBarUtil;

/**
 * BaseActivity 本程序所有Activity的基类
 *
 * @author Flyman
 * created at 2017/4/1 5:05
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getName();
    protected Toast mShortToast;
    /**
     * 初始化控件
     */
    protected abstract void findWidget();

    /**
     * 初始化变量
     */
    protected abstract void initVariable();


    protected void getIntentFormActivity() {
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 显示时间较短Toast
     * @param content
     */

    protected void showShortToast(String content)
    {
        if(mShortToast == null)
        {
            mShortToast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
        }
        else {
            mShortToast.cancel();
            mShortToast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
        }
        mShortToast.show();
    }
    /**
     * 显示时间较短Toast
     * @param content
     */

    protected void showLongToast(String content)
    {
        if(mShortToast == null)
        {
            mShortToast = Toast.makeText(this, content, Toast.LENGTH_LONG);
        }
        else {
            mShortToast.cancel();
            mShortToast = Toast.makeText(this, content, Toast.LENGTH_LONG);
        }
        mShortToast.show();
    }



    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
}
