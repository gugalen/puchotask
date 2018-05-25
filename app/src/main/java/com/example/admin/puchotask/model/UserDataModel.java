package com.example.admin.puchotask.model;

import com.example.admin.puchotask.utilities.FirebaseConstants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserDataModel  implements Serializable {
    String userId;
    String userEmail;
    public UserDataModel() {
    }

    public UserDataModel(String userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public UserDataModel(DataSnapshot snapshot) {
        this.userId = snapshot.child(FirebaseConstants.User.USER_ID).getValue().toString();
        if(snapshot.child(FirebaseConstants.User.USER_EMAIL) != null) {
            this.userEmail = snapshot.child(FirebaseConstants.User.USER_EMAIL).getValue().toString();
        }
    }

    public UserDataModel(HashMap<String, Object> snapshot) {
        this.userId = snapshot.get(FirebaseConstants.User.USER_ID).toString();
        this.userEmail = snapshot.get(FirebaseConstants.User.USER_EMAIL).toString();
    }

    public String getUserId() {
        return userId;
    }
    public String getUserEmail() {
        return userEmail;
    }

    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put(FirebaseConstants.User.USER_ID, userId);
        result.put(FirebaseConstants.User.USER_EMAIL, userEmail);
        return result;
    }

    @Exclude
    @Override
    public String toString() {
        return "UserDataModel{" +
                "userId='" + userId + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}