package com.flyman.app.web4android.functionmod.faqs.contract;

import com.flyman.app.web4android.base.IBasePresenter;
import com.flyman.app.web4android.base.IBaseView;
import com.flyman.app.web4android.functionmod.main.contract.MainContract;

public interface FAQsContract {
    interface View extends IBaseView<MainContract.Presenter> {
    }

    interface Presenter extends IBasePresenter {

    }
}
