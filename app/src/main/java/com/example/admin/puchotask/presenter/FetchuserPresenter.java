package com.example.admin.puchotask.presenter;

import android.util.Log;

import com.example.admin.puchotask.model.UserDataModel;
import com.example.admin.puchotask.utilities.FirebaseConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class FetchuserPresenter extends FetchuserContract.Presenter {
    private static final String TAG = "FetchUserPresenter";
    private FetchuserContract.View view;
    private FirebaseAuth firebaseAuth;

    @Override
    public void attachView(FetchuserContract.View view) {
        this.view = view;
    }

    @Override
    public void attachStorageReference(StorageReference storageReference) {
    }

    @Override
    public void attachFirebaseAuth(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void getUserByEmail(final String email) {
        Query query = FirebaseDatabase.getInstance().getReference(FirebaseConstants.User.TABLE_NAME)
                .orderByChild(FirebaseConstants.User.USER_EMAIL)
                .equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange:" + dataSnapshot);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child(FirebaseConstants.User.USER_EMAIL).getValue().toString().equals(email)) {
                        UserDataModel userDataModel = new UserDataModel(snapshot);
                        view.onUserFetched(userDataModel);
                    }
                }
                if(dataSnapshot.getValue() == null) {
                    view.onUserFetchedFailed();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "iterating Rock onCancelled :" + databaseError.getDetails());
            }
        });
    }
}
