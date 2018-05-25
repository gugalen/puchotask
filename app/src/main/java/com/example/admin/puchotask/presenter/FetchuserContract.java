package com.example.admin.puchotask.presenter;

import com.example.admin.puchotask.model.UserDataModel;

public class FetchuserContract {
    public interface View extends BaseView {
        void onUserFetched(UserDataModel userDataModel);
        void onUserFetchedFailed();
        void onError(String error);
    }

    public static abstract class Presenter extends BasePresenter<View> {
        public abstract void getUserByEmail(String email);
    }
}
