package com.example.admin.puchotask.presenter;

import android.util.Log;

import com.example.admin.puchotask.model.UserDataModel;
import com.example.admin.puchotask.utilities.FirebaseConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class CreateUserPresenter extends CreateUserContract.Presenter {

    private CreateUserContract.View view;
    private FirebaseAuth firebaseAuth;

    @Override
    public void attachView(CreateUserContract.View view) {
        this.view = view;
    }

    @Override
    public void attachStorageReference(StorageReference storageReference) {
    }

    @Override
    public void attachFirebaseAuth(FirebaseAuth firebaseAuth) {
    }

    @Override
    public void storeUser(UserDataModel userDataModel) {
        {
            Log.e(BasePresenter.LOG_TAG, "storeUser: " + userDataModel.toString());
            DatabaseReference finalRef = FirebaseDatabase
                    .getInstance()
                    .getReference(FirebaseConstants.User.TABLE_NAME).child(userDataModel.getUserId());
            finalRef.setValue(userDataModel.toMap());
            view.onUserSaveSuccess();
        }
    }
}