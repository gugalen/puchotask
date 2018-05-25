package com.example.admin.puchotask.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;

public abstract class BasePresenter<T extends BaseView> {
    public static final String LOG_TAG = "BasePresenter";
    public abstract void attachView(T view);
    public abstract void attachStorageReference(StorageReference storageReference);
    public abstract void attachFirebaseAuth(FirebaseAuth firebaseAuth);
}