package com.joojang.bookfriend.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.joojang.bookfriend.BookRegistFragment;
import com.joojang.bookfriend.MainActivity;
import com.joojang.bookfriend.R;
import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.data.GetKakaoBookSearchResponse;
import com.joojang.bookfriend.data.LoginResponse;
import com.joojang.bookfriend.model.KakaoBookDocument;
import com.joojang.bookfriend.model.LoginUser;
import com.joojang.bookfriend.utils.Tools;


public class LoginSimpleLight extends AppCompatActivity {

    private final String TAG = LoginSimpleLight.class.getSimpleName();

    private RetroClient retroClient;

    TextInputEditText et_login_email , et_login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_simple_light);

        retroClient = RetroClient.getInstance(this).createBaseApi();

        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);


        et_login_email = findViewById(R.id.et_login_email);
        et_login_password = findViewById(R.id.et_login_password);

        ((View) findViewById(R.id.sign_up)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext() , Join.class);
                startActivity(intent);
            }
        });

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //proc_login();
                login(null);
            }
        });
    }

    private void proc_login(){
        Log.d(TAG, "proc_login");

        String login_email = et_login_email.getText().toString().trim();
        String login_password = et_login_password.getText().toString().trim();

        login_email="sm.park@healthmax.co.kr";
        login_password="1212";

        if ( login_email == null || login_email.length() == 0 ){
            Toast.makeText( this ,"아이디를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        if ( login_password == null || login_password.length() == 0 ){
            Toast.makeText( this ,"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }

        LoginUser loginUser = new LoginUser();
        loginUser.setEmail(login_email);
        loginUser.setPassword(login_password);

        retroClient.login(loginUser , new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "login onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "login onSuccess :"+code);
                LoginResponse loginResponse = (LoginResponse) receiveData;
                if (loginResponse != null) {
                    Log.d(TAG, "login result : " + loginResponse.toString() );
                    login(loginResponse);
                }
            }
        });
    }

    private void login(LoginResponse loginResponse){
        Intent intent = new Intent( getApplicationContext() , MainActivity.class);
        startActivity(intent);
        finish();
    }
}
