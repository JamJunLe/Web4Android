package com.flyman.app.web4android.functionmod.homepage.contract;

import com.flyman.app.web4android.base.IModuleCallback;
import com.flyman.app.web4android.base.IBasePresenter;
import com.flyman.app.web4android.base.IBaseView;
import com.flyman.app.web4android.moudle.bean.Msg;

public interface ArticleDetailsContract {

    interface View extends IBaseView {
        void showError(Msg msg);

        void onPresenterStart();
    }

    interface Presenter extends IBasePresenter {

    }

    interface Moudle extends IModuleCallback {

    }
}
