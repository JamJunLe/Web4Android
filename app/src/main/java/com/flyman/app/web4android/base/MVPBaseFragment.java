package com.flyman.app.web4android.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class MVPBaseFragment<V, P extends BasePresenter<V>> extends BaseFragment {
    protected P mPresenter;
    protected final String TAG = this.getClass().getName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (null != mPresenter) {
            mPresenter.attachView((V) this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detachView();
        }
    }

    protected abstract P createPresenter();
}
