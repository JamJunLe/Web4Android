package com.flyman.app.web4android.functionmod.homepage.contract;

import com.flyman.app.web4android.base.IBasePresenter;
import com.flyman.app.web4android.base.IBaseView;

import java.util.ArrayList;

public interface HomePageChildContract {
    interface View extends IBaseView {

        <T> void showErrorMsg(T t);

        void cleanViewState();//清除view的状态

        void onStartPresent();

        <T extends ArrayList> void showPullRefreshData(T data);

        <T extends ArrayList> void showPushLoadmoreData(T data);

        void setRefreshEnable(boolean isEnable);

        void setRefreshing(boolean isReFreshing);

        void setLoadMOreRefreshing(boolean isReFreshing);

    }

    interface Presenter extends IBasePresenter {
        void getLoadMoreData();

        void getPullRefreshData();

    }
}
