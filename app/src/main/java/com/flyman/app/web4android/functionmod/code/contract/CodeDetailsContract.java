package com.flyman.app.web4android.functionmod.code.contract;

import com.flyman.app.web4android.base.IBasePresenter;
import com.flyman.app.web4android.base.IBaseView;
import com.flyman.app.web4android.moudle.bean.Msg;

public interface CodeDetailsContract {

    interface View extends IBaseView {
        void showError(Msg msg);

        void onPresenterStart();
    }

    interface Presenter extends IBasePresenter {

    }
}
