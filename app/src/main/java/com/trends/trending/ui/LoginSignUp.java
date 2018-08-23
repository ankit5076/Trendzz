package com.trends.trending.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.trends.trending.MainActivity;
import com.trends.trending.R;
import com.trends.trending.utils.FirebaseHelper;
import com.trends.trending.utils.NetworkHelper;
import com.trends.trending.utils.SessionManagement;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginSignUp extends AppCompatActivity {

    private static final int RC_SIGN_IN = 111;
    private static final String TAG = "LoginSignUp";

    @BindView(R.id.editTextEmail)
    EditText mEditTextEmail;
    @BindView(R.id.editTextPassword)
    EditText mEditTextPassword;
    @BindView(R.id.editTextName)
    EditText mEditTextName;
    @BindView(R.id.buttonSignin)
    Button mButtonSignin;
    @BindView(R.id.textViewSignUp)
    TextView mTextViewSignUp;

    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        progressDialog = new ProgressDialog(this);

    }


    private void userLogin() {

        final String email = mEditTextEmail.getText().toString().trim();
        String password = mEditTextPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            //start the profile activity
                            SessionManagement sessionManagement = new SessionManagement(LoginSignUp.this);
                            if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                                sessionManagement.updateNewUser(true);
                            }
                            sessionManagement.updateNewUser(false);
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    mEditTextPassword.setError(getString(R.string.error_wrong_password));
                    mEditTextPassword.requestFocus();
                } else if (e instanceof FirebaseAuthInvalidUserException) {
                    mEditTextEmail.setError(getString(R.string.error_invalid_email));
                    mEditTextEmail.requestFocus();
                } else {
                    Toast.makeText(LoginSignUp.this, getString(R.string.try_after_some_time),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @OnClick({R.id.buttonSignin, R.id.googleSignInButton, R.id.textViewSignUp, R.id.forgot_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonSignin:
                if (mButtonSignin.getText().equals(getResources().getString(R.string.sign_in))) {
                    userLogin();
                } else {
                    signUpWithEmail();
                }
                break;
            case R.id.googleSignInButton:
                signUpWithGoogle();
                break;
            case R.id.textViewSignUp:
                if (mTextViewSignUp.getText().equals(getResources().getString(R.string.signup_here))) {
                    mEditTextName.setVisibility(View.VISIBLE);
                    mEditTextName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_left));
                    mEditTextName.requestFocus();
                    mButtonSignin.setText(getResources().getString(R.string.sign_up));
                    mTextViewSignUp.setText(getResources().getString(R.string.signin_here));
                } else {
                    mEditTextName.setVisibility(View.GONE);
                    mEditTextName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
                    mButtonSignin.setText(getResources().getString(R.string.sign_in));
                    mTextViewSignUp.setText(getResources().getString(R.string.signup_here));

                }
                break;

            case R.id.forgot_pwd:
                LayoutInflater inflater = getLayoutInflater();
                View alertForgotPwd = inflater.inflate(R.layout.alert_forgot_password, null);
                final EditText email = alertForgotPwd.findViewById(R.id.alert_email);

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(getResources().getString(R.string.alert_forgot_password));
                alert.setView(alertForgotPwd);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                alert.setNegativeButton(getResources().getString(R.string.alert_cancel), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton(getResources().getString(R.string.alert_send), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (NetworkHelper.hasNetworkAccess(LoginSignUp.this)) {
                            String userEmail = email.getText().toString().trim();
                            if (userEmail.length() > 0) {
                                FirebaseAuth.getInstance().sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(LoginSignUp.this, getResources().getString(R.string.alert_forgot_pwd_success_msg), Toast.LENGTH_SHORT).show();
                                        } else
                                            Toast.makeText(LoginSignUp.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else
                                Toast.makeText(LoginSignUp.this, "Enter your registered email", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(LoginSignUp.this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

                break;
        }
    }

    private void signUpWithEmail() {

        String email = mEditTextEmail.getText().toString().trim();
        String pwd = mEditTextPassword.getText().toString().trim();
        final String nickName = mEditTextName.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(nickName)) {
            Toast.makeText(this, "Please enter nick name", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage(getString(R.string.progress_account_creation_title));
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = task.getResult().getUser();
                            addUser(firebaseUser.getUid(), nickName, firebaseUser.getEmail());
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {

                            try {
                                if (task.getException() != null)
                                    throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                mEditTextPassword.setError(getString(R.string.error_weak_password));
                                mEditTextPassword.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                mEditTextEmail.setError(getString(R.string.error_invalid_email));
                                mEditTextEmail.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                mEditTextEmail.setError(getString(R.string.error_user_exists));
                                mEditTextEmail.requestFocus();
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(LoginSignUp.this, getString(R.string.try_after_some_time) + task.getException(), Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    private void addUser(String uid, String nickName, String email) {
        if (new FirebaseHelper(FirebaseDatabase.getInstance().getReference(), LoginSignUp.this)
                .writeNewUser(uid, nickName, email)) {
            Toast.makeText(this, "user added", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "user not added", Toast.LENGTH_SHORT).show();
    }

    private void signUpWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(LoginSignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null && user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp())
                                addUser(user.getUid(), user.getDisplayName(), user.getEmail());
                            startActivity(new Intent(LoginSignUp.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginSignUp.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {
            FirebaseUserMetadata metadata = firebaseAuth.getCurrentUser().getMetadata();
            if (metadata != null && metadata.getCreationTimestamp() == metadata.getLastSignInTimestamp()) {
                Toast.makeText(this, "new user", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "exsisting user", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
