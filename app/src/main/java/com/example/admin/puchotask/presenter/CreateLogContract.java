package com.example.admin.puchotask.presenter;

import com.example.admin.puchotask.model.Log;

public class CreateLogContract {
    public interface View extends BaseView {
        void onLogSaveSuccess();
        void onLogSaveFailed();
        void onError(String error);
    }

    public static abstract class Presenter extends BasePresenter<View> {
        public abstract void storeLog (Log log);
    }
}
