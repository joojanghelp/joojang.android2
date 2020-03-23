package com.joojang.bookfriend.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.joojang.bookfriend.BaseApplication;
import com.joojang.bookfriend.MainActivity;
import com.joojang.bookfriend.R;
import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.dataResponse.LoginResponse;
import com.joojang.bookfriend.model.RefreshToken;
import com.joojang.bookfriend.utils.Tools;
import com.joojang.bookfriend.utils.Util;

import java.util.Date;

public class Splash extends AppCompatActivity {

    private final String TAG = Splash.class.getSimpleName();
    private RetroClient retroClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);

        retroClient = RetroClient.getInstance(this).createBaseApi();

        String saveLoginToken = Util.getAccessTokenPreferences(this);
        Log.d(TAG,"saveLoginToken="+saveLoginToken);
        if (saveLoginToken != null && saveLoginToken.length() != 0) {
            autoLogin();
        }else{
            Intent intent = new Intent( getApplicationContext() , LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

    private void autoLogin() {
        String rtoken = Util.getRefreshTokenPreferences(this);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefresh_token(rtoken);

        retroClient.refreshToken(refreshToken, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "refreshToken onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "refreshToken onSuccess :" + code);
                LoginResponse loginResponse = (LoginResponse) receiveData;
                if (loginResponse != null) {
                    Log.d(TAG, "refreshToken result : " + loginResponse.toString());
                    login(loginResponse);
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
