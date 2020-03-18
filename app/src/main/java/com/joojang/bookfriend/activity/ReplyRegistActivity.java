package com.joojang.bookfriend.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.joojang.bookfriend.R;
import com.joojang.bookfriend.utils.Tools;

public class ReplyRegistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_regist);

        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);

        (findViewById(R.id.et_reply_catetory)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStateDialog(v);
            }
        });
    }

    private void showStateDialog(final View v) {
        final String[] array = new String[]{
                "느낀점 쓰기","뒷이야기 꾸미기", "편지 쓰기 ", "상상 일기 쓰기 "
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("독서 활동 선택");
        builder.setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) v).setText(array[i]);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
