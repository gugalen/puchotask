package com.example.admin.puchotask.presenter;

import com.example.admin.puchotask.model.UserDataModel;

public class CreateUserContract {
    public interface View extends BaseView {
        void onUserSaveSuccess();
        void onUserSaveFailed();
        void onError(String error);
    }

    public static abstract class Presenter extends BasePresenter<View> {
        public abstract void storeUser(UserDataModel userDataModel);
    }
}
