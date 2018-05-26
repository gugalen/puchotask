package com.example.admin.puchotask.presenter;

import com.example.admin.puchotask.model.Log;
import com.example.admin.puchotask.utilities.FirebaseConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class CreateLogPresenter extends CreateLogContract.Presenter{
    private CreateLogContract.View view;
    private FirebaseAuth firebaseAuth;

    @Override
    public void storeLog(Log log) {
        {
            Log.e(BasePresenter.LOG_TAG, "storeUser: " + log.toString());
            DatabaseReference finalRef = FirebaseDatabase
                    .getInstance()
                    .getReference(FirebaseConstants.User.TABLE_NAME).child(log.getUserId());
            finalRef.setValue(log.toMap());
            view.onLogSaveSuccess();
        }
    }

    @Override
    public void attachView(CreateLogContract.View view) {
        this.view = view;
    }

    @Override
    public void attachStorageReference(StorageReference storageReference) {

    }

    @Override
    public void attachFirebaseAuth(FirebaseAuth firebaseAuth) {

    }
}
