package com.flyman.app.web4android.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;


public abstract class BaseFragment extends Fragment {

    protected Toast mShortToast;
    protected final String TAG = this.getClass().getName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void findWidget() {
    }

    protected void initVariable() {
    }

    /**
     * 显示时间较短Toast
     *
     * @param content
     */

    protected void showShortToast(String content) {
        if (mShortToast == null) {
            mShortToast = Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT);
        } else {
            mShortToast.cancel();
            mShortToast = Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT);
        }
        mShortToast.show();
    }

    /**
     * 显示时间较短Toast
     *
     * @param content
     */

    protected void showLongToast(String content) {
        if (mShortToast == null) {
            mShortToast = Toast.makeText(getActivity(), content, Toast.LENGTH_LONG);
        } else {
            mShortToast.cancel();
            mShortToast = Toast.makeText(getActivity(), content, Toast.LENGTH_LONG);
        }
        mShortToast.show();
    }


}
