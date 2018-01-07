package com.flyman.app.web4android.base;

import java.util.ArrayList;

public interface IListView extends IBaseView {

    void cleanViewState();//清除view的状态

    <T> void showErrorMsg(T t);

    <T extends ArrayList> void showPullRefreshData(T data);

    <T extends ArrayList> void showPushLoadMoreData(T data);

    void setRefreshEnable(boolean isEnable);

    void setRefreshing(boolean isRefreshing);

    void setLoadMOreRefreshing(boolean isRefreshing);
}
