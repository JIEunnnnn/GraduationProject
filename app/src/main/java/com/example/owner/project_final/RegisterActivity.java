package com.example.owner.project_final;

/**
 * ViewPager - Fragment
 * TabHost (Failed)
 */

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.owner.project_final.firebase.FirebaseApi;
import com.example.owner.project_final.firebase.MessageUtils;
import com.example.owner.project_final.firebase.OnResultListener;
import com.example.owner.project_final.firebase.PublicVariable;
import com.example.owner.project_final.model.UserModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    //[오투잡] 2019.04.13 회원가입 로직변경

    @BindView(R.id.registerActivity_edittext_email)
    EditText mEmailEdit;

    @BindView(R.id.registerActivity_edittext_password)
    EditText mPasswrodEdit;

    @BindView(R.id.registerActivity_edittext_name)
    EditText mNickEdit;

    @BindView(R.id.registerActivity_edittext_address)
    EditText address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mNickEdit.getText().toString())) {
            mNickEdit.setError("Required");
            result = false;
        } else if (mNickEdit.getText().length() < 1 && mNickEdit.getText().length() > 9) {
            mNickEdit.setError("2~10자 이내로 입력해주세요.");
            result = false;
        } else {
            mEmailEdit.setError(null);
        }

        if (TextUtils.isEmpty(mEmailEdit.getText().toString())) {
            mEmailEdit.setError("Required");
            result = false;
        } else {
            mEmailEdit.setError(null);
        }

        if (TextUtils.isEmpty(mPasswrodEdit.getText().toString())) {
            mPasswrodEdit.setError("Required");
            result = false;
        } else if (mPasswrodEdit.getText().length() < 7 && mPasswrodEdit.getText().length() > 21) {
            mPasswrodEdit.setError("8~20자 이내로 입력해주세요.");
            result = false;
        } else {
            mPasswrodEdit.setError(null);
        }

        return result;
    }

    @OnClick(R.id.registerActivity_button_signup)
    public void registerUser(View view) {
        if (!validateForm()) {
            return;
        }
        checkVerfication(mNickEdit.getText().toString(), mEmailEdit.getText().toString(), mPasswrodEdit.getText().toString());
    }

    private void checkVerfication(final String nick, String email, final String password) {
        FirebaseApi.createToUserID(email, password, new OnResultListener() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task != null) {
                    registerSuccess(task.getResult().getUser(), nick, password);
                }
            }

            @Override
            public void onFailure(Exception e) {
                if (e instanceof FirebaseAuthUserCollisionException) {
                    MessageUtils.showLongToastMsg(getApplicationContext(), e.getMessage());
                } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    MessageUtils.showLongToastMsg(getApplicationContext(), e.getMessage());
                }
            }
        });
    }


    private void registerSuccess(FirebaseUser firebaseUser, String nick, String password) {

        L.i("[onAuthSuccess] " + firebaseUser.getUid() + " , " + firebaseUser.getEmail());
        String name = usernameFromEmail(firebaseUser.getEmail());
        String unique_key = firebaseUser.getUid();

        UserModel userModel = new UserModel(nick, firebaseUser.getEmail(), password, "");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        if (databaseReference != null) {
            databaseReference.child(PublicVariable.FIREBASE_CHILD_USERS).child(unique_key).setValue(userModel);
            onSucces();
        }
    }

    private void onSucces() {
        showSnakbar("회원 가입에 성공 하였습니다.");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseApi.logout();
                overridePendingTransition(0, 0);
                finish();
            }
        }, 1500);

    }


    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    public void showSnakbar(String text) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
        }
    }
}
