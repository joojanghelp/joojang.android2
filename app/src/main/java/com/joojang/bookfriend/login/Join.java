package com.joojang.bookfriend.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.joojang.bookfriend.MainActivity;
import com.joojang.bookfriend.R;
import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.data.JoinResponse;
import com.joojang.bookfriend.data.LoginResponse;
import com.joojang.bookfriend.model.JoinUser;
import com.joojang.bookfriend.model.LoginUser;
import com.joojang.bookfriend.utils.Tools;



public class Join extends AppCompatActivity {

    private final String TAG = Join.class.getSimpleName();

    private RetroClient retroClient;

    TextInputEditText et_join_email, et_join_name, et_join_password1, et_join_password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        retroClient = RetroClient.getInstance(this).createBaseApi();

        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);

        et_join_email = findViewById(R.id.et_join_email);
        et_join_name = findViewById(R.id.et_join_name);
        et_join_password1 = findViewById(R.id.et_join_password1);
        et_join_password2 = findViewById(R.id.et_join_password2);

        et_join_name.setText("sm1");
        et_join_email.setText("sm1.park@healthmax.co.kr");
        et_join_password1.setText("1212");

        Button btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proc_join();
            }
        });
    }

    private void proc_join(){
        Log.d(TAG, "proc_join");

        String join_email = et_join_email.getText().toString().trim();
        String join_name = et_join_name.getText().toString().trim();
        String join_password = et_join_password1.getText().toString().trim();



        if ( join_name == null || join_name.length() == 0 ){
            Toast.makeText( this ,"이름을 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        if ( join_email == null || join_email.length() == 0 ){
            Toast.makeText( this ,"이메일을 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        if ( join_password == null || join_password.length() == 0 ){
            Toast.makeText( this ,"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }

        JoinUser joinUser = new JoinUser();
        joinUser.setName(join_name);
        joinUser.setEmail(join_email);
        joinUser.setPassword(join_password);

        retroClient.register(joinUser , new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "login onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "register onSuccess :"+code);
                JoinResponse joinResponse = (JoinResponse) receiveData;
                if (joinResponse != null) {
                    Log.d(TAG, "login result : " + joinResponse.toString() );
                    join(joinResponse);
                }
            }
        });
    }

    private void join(JoinResponse joinResponse){
        Intent intent = new Intent( getApplicationContext() , MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
