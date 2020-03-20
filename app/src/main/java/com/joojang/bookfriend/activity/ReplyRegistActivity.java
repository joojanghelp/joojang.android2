package com.joojang.bookfriend.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joojang.bookfriend.R;
import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.dataResponse.DefaultResponse;
import com.joojang.bookfriend.model.Book;
import com.joojang.bookfriend.model.BookReply;
import com.joojang.bookfriend.utils.Tools;

public class ReplyRegistActivity extends AppCompatActivity {

    private final String TAG = ReplyRegistActivity.class.getSimpleName();

    private int mSelectedItem = -1;
    private String mSelectedCode;
    private int mBook_id;

    private EditText et_reply_content;
    private Button btn_re_write;

    private RetroClient retroClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_regist);

        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);

        retroClient = RetroClient.getInstance(this).createBaseApi();

        mBook_id = getIntent().getIntExtra("book_id", 0);

        et_reply_content = findViewById(R.id.et_reply_content);
        btn_re_write = findViewById(R.id.btn_re_write);

        btn_re_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proc_replyWrite();
            }
        });

        (findViewById(R.id.et_reply_catetory)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStateDialog(v);
            }
        });
    }

    private void showStateDialog(final View v) {
        final String[] array_name = new String[]{"느낀점 쓰기", "뒷이야기 꾸미기", "편지 쓰기 ", "상상 일기 쓰기 "};
        final String[] array_code = new String[]{"C11000", "C11110", "C11120", "C11130"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("독서 활동 선택");
        builder.setSingleChoiceItems(array_name, mSelectedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) v).setText(array_name[i]);
                mSelectedItem = i;
                mSelectedCode = array_code[i];
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void proc_replyWrite() {
        String contents = et_reply_content.getText().toString();
        if (contents == null || contents.length() == 0) {
            return;
        }

        // book_id , gubun , contents
        // mBook_id , mSelectedCode , contents

        BookReply bookReply = new BookReply();
        bookReply.setBook_id(mBook_id);
        bookReply.setGubun(mSelectedCode);
        bookReply.setContents(contents);

        retroClient.registerReply(bookReply, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "registerBook onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "registerBook onSuccess ");

                DefaultResponse defaultResponse = (DefaultResponse) receiveData;

                if (code == 201) {
                    Log.d(TAG, "defaultResponse result : " + defaultResponse.getMessage());
                } else {
                    Log.d(TAG, "defaultResponse result : " + code);
                }
            }

            @Override
            public void onFail(int code, String message) {
                Log.d(TAG, "onFail : " + code);
                Log.d(TAG, "onFail : " + message);
                Toast.makeText( getApplicationContext() , message, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
