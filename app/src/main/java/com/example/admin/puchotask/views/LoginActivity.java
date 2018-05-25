package com.example.admin.puchotask.views;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.admin.puchotask.R;
import com.example.admin.puchotask.model.UserDataModel;
import com.example.admin.puchotask.presenter.CreateUserContract;
import com.example.admin.puchotask.presenter.CreateUserPresenter;
import com.example.admin.puchotask.presenter.FetchuserContract;
import com.example.admin.puchotask.presenter.FetchuserPresenter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.storage.FirebaseStorage;

public class LoginActivity extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener,CreateUserContract.View, FetchuserContract.View {

    private static final String TAG = "LoginActivity";
    private static final String LOG_TAG = com.example.admin.puchotask.views.LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    SignInButton btnGoogleSignIn;
    private CreateUserPresenter createUserPresenter;
    FetchuserPresenter fetchUserPresenter;

    private void initPresenter() {
        createUserPresenter = new CreateUserPresenter();
        createUserPresenter.attachStorageReference(FirebaseStorage.getInstance().getReference());
        createUserPresenter.attachFirebaseAuth(FirebaseAuth.getInstance());
        createUserPresenter.attachView(this);

        fetchUserPresenter = new FetchuserPresenter();
        fetchUserPresenter.attachView(this);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initPresenter();

        btnGoogleSignIn = findViewById(R.id.login_with_google);
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(LoginActivity.this, "Signed in", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Signed out", Toast.LENGTH_LONG).show();
                }
            }
        };

        btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.addAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (googleSignInResult.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = googleSignInResult.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                Log.e(TAG, "Google sign in failed");
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
//                            UserPreferencesUtility.getInstance(LoginActivity.this).setLoginUserData(true);
                            FirebaseUser user = mAuth.getCurrentUser();
                            fetchUserPresenter.getUserByEmail(user.getEmail());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void addGoogleSignInUserTable() {
        // create and add user in firebase table
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        String email = currentUser.getEmail();
        UserDataModel userDataModel =  new UserDataModel(uid,email,"","");
        Log.e(TAG, "addUserInUserTable: " + userDataModel.toString());
        createUserPresenter.storeUser(userDataModel);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Log.e(LOG_TAG, "------USER----");
            Log.e(LOG_TAG, "name:" + user.getDisplayName());
            Log.e(LOG_TAG, "photo:" + user.getPhotoUrl());
            Log.e(LOG_TAG, "uid:" + user.getUid());

            TextToSpeechActivity.start(LoginActivity.this);

            LoginActivity.this.finish();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onUserSaveSuccess() {
        Log.e(TAG, "onUserSaveSuccess: user saved successfully");
    }

    @Override
    public void onUserSaveFailed() {
        Log.e(TAG, "onUserSaveFailed: failed to save the user");
    }

    @Override
    public void onUserFetched(UserDataModel userDataModel) {
        if(userDataModel == null) {
            Log.e(TAG, "onUserFetched: null data");
            FirebaseUser user = mAuth.getCurrentUser();
            Log.e(TAG, "onUserFetched: google login");
            addGoogleSignInUserTable();
            updateUI(user);
        } else {
            Log.e(TAG, "onUserFetched: user present");
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }
    }

    @Override
    public void onUserFetchedFailed() {
        Log.e(TAG, "onUserFetched: null data");
        FirebaseUser user = mAuth.getCurrentUser();
        Log.e(TAG, "onUserFetched: google login");
        addGoogleSignInUserTable();
        updateUI(user);
    }

    @Override
    public void onError(String error) {
        Log.e(TAG, "onUserFetched: null data");
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void dismissLoadingView() {

    }

    @Override
    public void showNetworkError() {

    }
}