package com.example.admin.puchotask.model;

import com.example.admin.puchotask.utilities.FirebaseConstants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Log {
    String userId;
    String inputText;
    String outputAudio;
    String createdAt;
    public Log() {
    }

    public Log(String userId, String userEmail) {
        this.userId = userId;
        this.inputText = inputText;
        this.outputAudio = outputAudio;
        this.createdAt = createdAt;
    }

    public Log(DataSnapshot snapshot) {
        this.userId = snapshot.child(FirebaseConstants.User.USER_ID).getValue().toString();
        if(snapshot.child(FirebaseConstants.User.INPUT_TEXT) != null) {
            this.inputText = snapshot.child(FirebaseConstants.User.INPUT_TEXT).getValue().toString();
        }
        this.outputAudio = snapshot.child(FirebaseConstants.User.OUTPUT_AUDIO).getValue().toString();
        this.createdAt = snapshot.child(FirebaseConstants.User.CREATED_AT).getValue().toString();
    }

    public Log(HashMap<String, Object> snapshot) {
        this.userId = snapshot.get(FirebaseConstants.User.USER_ID).toString();
        this.inputText = snapshot.get(FirebaseConstants.User.INPUT_TEXT).toString();
        this.outputAudio = snapshot.get(FirebaseConstants.User.OUTPUT_AUDIO).toString();
        this.createdAt = snapshot.get(FirebaseConstants.User.CREATED_AT).toString();
    }

    public String getUserId() {
        return userId;
    }
    public String getInputText() {
        return inputText;
    }
    public String getOutputAudio() {
        return outputAudio;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put(FirebaseConstants.User.USER_ID, userId);
        result.put(FirebaseConstants.User.INPUT_TEXT, inputText);
        result.put(FirebaseConstants.User.OUTPUT_AUDIO, outputAudio);
        result.put(FirebaseConstants.User.CREATED_AT, createdAt);
        return result;
    }

    @Exclude
    @Override
    public String toString() {
        return "Log" +
                ".{" +
                "userId='" + userId + '\'' +
                ", inputText='" + inputText + '\'' +
                ", userEmail='" + inputText + '\'' +
                ", outputAudio='" + outputAudio + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
