package com.joojang.bookfriend.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.joojang.bookfriend.BaseApplication;
import com.joojang.bookfriend.MainActivity;
import com.joojang.bookfriend.R;
import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.dataResponse.LoginResponse;
import com.joojang.bookfriend.model.LoginUser;
import com.joojang.bookfriend.model.RefreshToken;
import com.joojang.bookfriend.utils.Tools;
import com.joojang.bookfriend.utils.Util;

import java.io.IOException;
import java.util.Date;

import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private final String TAG = LoginActivity.class.getSimpleName();

    private RetroClient retroClient;

    TextInputEditText et_login_email, et_login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        retroClient = RetroClient.getInstance(this).createBaseApi();

        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);


        et_login_email = findViewById(R.id.et_login_email);
        et_login_password = findViewById(R.id.et_login_password);

        ((View) findViewById(R.id.sign_up)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proc_login();
            }
        });

    }

    private void proc_login() {
        Log.d(TAG, "proc_login");

        String login_email = et_login_email.getText().toString().trim();
        String login_password = et_login_password.getText().toString().trim();

        if (!Util.validateEmail(login_email)) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

//        if ( !Util.validateEmail(login_password) ){
//            Toast.makeText( this ,"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
//            return;
//        }

        if (login_email == null || login_email.length() == 0) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (login_password == null || login_password.length() == 0) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginUser loginUser = new LoginUser();
        loginUser.setEmail(login_email);
        loginUser.setPassword(login_password);

        retroClient.login(loginUser, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "login onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "login onSuccess :" + code);
                LoginResponse loginResponse = (LoginResponse) receiveData;
                if (loginResponse != null) {
                    Log.d(TAG, "login result : " + loginResponse.toString());
                    login(loginResponse);
//                    login(null);
                }
            }

            @Override
            public void onFail(int code, String message) {
                Log.d(TAG, "onFail : " + code);
                Log.d(TAG, "onFail : " + message);
                Toast.makeText(getApplicationContext(), code + ":" + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(LoginResponse loginResponse) {

        if (loginResponse != null && loginResponse.getAccess_token() != null) {
            BaseApplication.getInstance().setLOGINTOKEN(loginResponse.getAccess_token());
            BaseApplication.getInstance().setREFRESHTOKEN(loginResponse.getRefresh_token());
            BaseApplication.getInstance().setEXPIRES_IN(loginResponse.getExpires_in());
            BaseApplication.getInstance().setLOGINDATE(new Date());

            Util.saveAccessTokenPreferences(this, loginResponse.getAccess_token());
            Util.saveRefreshTokenPreferences(this, loginResponse.getRefresh_token());

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }

    }


}
