package com.joojang.bookfriend.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joojang.bookfriend.R;
import com.joojang.bookfriend.adapter.AdapterListBookReply;
import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.dataResponse.BookDetailResponse;
import com.joojang.bookfriend.dataResponse.BookListResponse;
import com.joojang.bookfriend.dataResponse.DefaultResponse;
import com.joojang.bookfriend.model.ActivitySetting;
import com.joojang.bookfriend.model.BookReply;
import com.joojang.bookfriend.model.ReadState;
import com.joojang.bookfriend.utils.ConfirmDialogCallback;
import com.joojang.bookfriend.utils.Tools;
import com.joojang.bookfriend.utils.Util;
import com.joojang.bookfriend.widget.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class BookDetailActivity extends AppCompatActivity {

    private final String TAG = BookDetailActivity.class.getSimpleName();
    private RetroClient retroClient;

    private List<BookReply> items = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterListBookReply mAdapter;

    private TextView tv_content, tv_author, tv_publisher, tv_book_title;

    private Button btn_read_check;
    private boolean read_check;

    private int mBook_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        retroClient = RetroClient.getInstance(this).createBaseApi();

        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);


        mBook_id = getIntent().getIntExtra("book_id", 0);

        if (mBook_id == 0) finish();

        initComponent();

    }

    @Override
    protected void onResume() {
        proc_bookDetail(mBook_id);
        super.onResume();
    }

    private void initComponent() {

        tv_book_title = findViewById(R.id.tv_book_title);
        tv_author = findViewById(R.id.tv_author);
        tv_publisher = findViewById(R.id.tv_publisher);
        tv_content = findViewById(R.id.tv_content);

        mAdapter = new AdapterListBookReply(this, items);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.addItemDecoration(new SpacingItemDecoration(1, Tools.dpToPx(this, 12), true));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemLongClickListener(new AdapterListBookReply.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, BookReply obj, int position) {
                final String activity_id = obj.getActivity_id();
                Util.showConfirmDialog(BookDetailActivity.this, "삭제", "삭제 하시겠습니까?", new ConfirmDialogCallback() {
                    @Override
                    public void resultConfirmDialog(boolean result) {
                        proc_delReply(activity_id);
                    }
                });
            }
        });

        btn_read_check = findViewById(R.id.btn_read_check);

        Button btn_replay_regist = findViewById(R.id.btn_re_write);
        btn_replay_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailActivity.this, ReplyRegistActivity.class);
                intent.putExtra("book_id", mBook_id);
                startActivity(intent);
            }
        });

        btn_read_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                if (!read_check) {
                    msg = "책을 읽으셨습니까?";
                    Util.showConfirmDialog(BookDetailActivity.this, "독서 확인", msg,
                            new ConfirmDialogCallback() {
                                @Override
                                public void resultConfirmDialog(boolean result) {
                                    if (result) {
                                        proc_readState();
                                    }
                                }
                            });
                } else {
                    msg = "책을 읽지 않음으로 변경합니다.";
                    Util.showConfirmDialog(BookDetailActivity.this, "독서 확인", msg,
                            new ConfirmDialogCallback() {
                                @Override
                                public void resultConfirmDialog(boolean result) {
                                    if (result) {
                                        proc_deleteReadState();
                                    }
                                }
                            });

                }

            }
        });

    }

    private void proc_delReply(String activity_id) {
        ActivitySetting activitySetting = new ActivitySetting();
        activitySetting.setActivity_id(activity_id);

        retroClient.deleteActivity(activitySetting, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "proc_delReply onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "proc_delReply onSuccess :" + code);
                DefaultResponse defaultResponse = (DefaultResponse) receiveData;
                if (defaultResponse != null) {
                    Toast.makeText(getApplicationContext(), "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                    proc_bookDetail(mBook_id);
                }
            }

            @Override
            public void onFail(int code, String message) {
                Log.d(TAG, "onFail : " + code);
                Log.d(TAG, "onFail : " + message);
            }
        });
    }

    private void proc_bookDetail(int book_id) {

        retroClient.getBookDetail(book_id, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "proc_bookDetail onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "proc_bookDetail onSuccess :" + code);
                BookDetailResponse bookDetailResponse = (BookDetailResponse) receiveData;
                if (bookDetailResponse != null) {
                    setData(bookDetailResponse);
                }
            }

            @Override
            public void onFail(int code, String message) {
                Log.d(TAG, "onFail : " + code);
                Log.d(TAG, "onFail : " + message);
            }
        });
    }

    private void setData(BookDetailResponse bookDetailResponse) {

        ImageView image = findViewById(R.id.image);
        Tools.displayImageOriginal(this, image, bookDetailResponse.getThumbnail());

        tv_book_title.setText(bookDetailResponse.getTitle());
        tv_author.setText(bookDetailResponse.getAuthors());
        tv_publisher.setText(bookDetailResponse.getPublisher());
        tv_content.setText(bookDetailResponse.getContents());

        if (bookDetailResponse.getBook_activity() != null) {
            items.clear();
            items.addAll(bookDetailResponse.getBook_activity());
            mAdapter.notifyDataSetChanged();
        } else {
            items.clear();
            mAdapter.notifyDataSetChanged();
        }

        read_check = bookDetailResponse.isRead_check();
        changeReadState();

    }

    private void proc_readState() {

        ReadState readState = new ReadState();
        readState.setBook_id(mBook_id);

        retroClient.changeReadState(readState, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "proc_readState onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "proc_readState onSuccess :" + code);
                DefaultResponse defaultResponse = (DefaultResponse) receiveData;
                if (defaultResponse != null) {
                    read_check = true;
                    changeReadState();
                }
            }

            @Override
            public void onFail(int code, String message) {
                Log.d(TAG, "onFail : " + code);
                Log.d(TAG, "onFail : " + message);
            }
        });
    }

    private void proc_deleteReadState() {

        ReadState readState = new ReadState();
        readState.setBook_id(mBook_id);

        retroClient.deleteReadState(readState, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "proc_deleteReadState onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "proc_deleteReadState onSuccess :" + code);
                DefaultResponse defaultResponse = (DefaultResponse) receiveData;
                if (defaultResponse != null) {
                    read_check = false;
                    changeReadState();
                }
            }

            @Override
            public void onFail(int code, String message) {
                Log.d(TAG, "onFail : " + code);
                Log.d(TAG, "onFail : " + message);
            }
        });
    }



    private void changeReadState() {

        if (read_check) {
            btn_read_check.setText("읽었음");
            btn_read_check.setBackgroundResource(R.drawable.btn_rect_grey_black);
        } else {
            btn_read_check.setText("읽음");
            btn_read_check.setBackgroundResource(R.drawable.btn_rect_primary);
        }

    }

    @Override
    public void finish() {

        String check = "N";
        if (this.read_check) {
            check = "Y";
        } else {
            check = "N";
        }

        Intent intent = new Intent();
        intent.putExtra("book_id", mBook_id);
        intent.putExtra("read_state", check);
        setResult(RESULT_OK, intent);

        super.finish();
    }

}
