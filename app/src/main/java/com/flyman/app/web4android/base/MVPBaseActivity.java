package com.flyman.app.web4android.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * FileName    : MVPBaseActivity
 * Description :
 * MVP Activity基类
 *
 * @author : flyman
 * @version : 1.0
 * @Date :  2017/12/9 19:35
 **/
public abstract class MVPBaseActivity<V, P extends BasePresenter<V>> extends BaseActivity {
    protected P mPresenter;

    /**
     * 强制子类实现该方法用以提供BasePresenter
     *
     * @param
     * @return nothing
     */
    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (null != mPresenter){
            mPresenter.attachView((V) this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter){
            mPresenter.attachView((V) this);
        }
        mPresenter = null;
    }


}
