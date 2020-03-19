package com.joojang.bookfriend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joojang.bookfriend.R;
import com.joojang.bookfriend.adapter.AdapterListBookReply;
import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.dataResponse.BookDetailResponse;
import com.joojang.bookfriend.dataResponse.BookListResponse;
import com.joojang.bookfriend.model.BookReply;
import com.joojang.bookfriend.utils.Tools;
import com.joojang.bookfriend.widget.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class BookDetailActivity extends AppCompatActivity {

    private final String TAG = BookDetailActivity.class.getSimpleName();
    private RetroClient retroClient;

    private RecyclerView recyclerView;
    private AdapterListBookReply mAdapter;

    private TextView tv_content,tv_author, tv_publisher, tv_ActionBarTitle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        retroClient = RetroClient.getInstance(this).createBaseApi();

        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);


        int book_id=getIntent().getIntExtra("book_id",0);

        if ( book_id == 0 ) finish();

        initComponent();
        proc_bookDetail(book_id);
    }

    private void initComponent(){

        tv_ActionBarTitle = findViewById(R.id.tv_ActionBarTitle);
        tv_author = findViewById(R.id.tv_author);
        tv_publisher = findViewById(R.id.tv_publisher);
        tv_content = findViewById(R.id.tv_content);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.addItemDecoration(new SpacingItemDecoration(1, Tools.dpToPx(this, 12), true));
        recyclerView.setHasFixedSize(true);

        Button btn_replay_regist = findViewById(R.id.btn_re_write);
        btn_replay_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( BookDetailActivity.this , ReplyRegistActivity.class);
                startActivity(intent);
            }
        });

        List<BookReply> items = new ArrayList<>();
        BookReply bookReply = new BookReply();
        bookReply.setUsername("내이름");
        bookReply.setRegistdate("2020/03/17");
        bookReply.setReplycategory("편지쓰기");
        bookReply.setReplycontent("미디어 아티스트 이와이 도시오의 『100층짜리 집』. 신선한 상상력이 살아 숨쉬는 새로운 감각의 숫자 그림책입니다. 별을 바라보기를 좋아하는 '도치'라는 소년이 하늘까지 닿는 이상하고 재미있는 100층짜리 집 꼭대기에 초대받으면서 벌어지는 모험 속으로 아이들을 안내합니다. 누가 도치를 초대했는지 만나봐요.  이 숫자 그림책은 아이들이 도치를 따라 10층씩마다 다람쥐, 개구리, 딱따구리, 박쥐, 거미 등 동물이 살고 있는 100층짜리 집을 탐험해");
        items.add(bookReply);

        bookReply = new BookReply();
        bookReply.setUsername("내이름");
        bookReply.setRegistdate("2020/03/17");
        bookReply.setReplycategory("편지쓰기");
        bookReply.setReplycontent("미디어 아티스트 이와이 도시오의 『100층짜리 집』. 신선한 상상력이 살아 숨쉬는 새로운 감각의 숫자 그림책입니다. 별을 바라보기를 좋아하는 '도치'라는 소년이 하늘까지 닿는 이상하고 재미있는 100층짜리 집 꼭대기에 초대받으면서 벌어지는 모험 속으로 아이들을 안내합니다. 누가 도치를 초대했는지 만나봐요.  이 숫자 그림책은 아이들이 도치를 따라 10층씩마다 다람쥐, 개구리, 딱따구리, 박쥐, 거미 등 동물이 살고 있는 100층짜리 집을 탐험해");
        items.add(bookReply);

        bookReply = new BookReply();
        bookReply.setUsername("내이름");
        bookReply.setRegistdate("2020/03/17");
        bookReply.setReplycategory("편지쓰기");
        bookReply.setReplycontent("미디어 아티스트 이와이 도시오의 『100층짜리 집』. 신선한 상상력이 살아 숨쉬는 새로운 감각의 숫자 그림책입니다. 별을 바라보기를 좋아하는 '도치'라는 소년이 하늘까지 닿는 이상하고 재미있는 100층짜리 집 꼭대기에 초대받으면서 벌어지는 모험 속으로 아이들을 안내합니다. 누가 도치를 초대했는지 만나봐요.  이 숫자 그림책은 아이들이 도치를 따라 10층씩마다 다람쥐, 개구리, 딱따구리, 박쥐, 거미 등 동물이 살고 있는 100층짜리 집을 탐험해");
        items.add(bookReply);

        mAdapter = new AdapterListBookReply(this, items);
        recyclerView.setAdapter(mAdapter);
    }

    private void proc_bookDetail(int book_id){

        retroClient.getBookDetail(book_id , new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "proc_bookDetail onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "proc_bookDetail onSuccess :"+code);
                BookDetailResponse bookDetailResponse = (BookDetailResponse) receiveData;
                if (bookDetailResponse != null) {
                    setData(bookDetailResponse);
                }
            }

            @Override
            public void onFail(int code, String message) {
                Log.d( TAG,"onFail : "+code);
                Log.d( TAG,"onFail : "+message);
            }
        });
    }

    private void setData(BookDetailResponse bookDetailResponse){

        ImageView image = findViewById(R.id.image);
        Tools.displayImageOriginal(this,image,bookDetailResponse.getThumbnail());

        tv_ActionBarTitle.setText( bookDetailResponse.getTitle() );
        tv_author.setText( bookDetailResponse.getAuthors() );
        tv_publisher.setText( bookDetailResponse.getPublisher() );
        tv_content.setText( bookDetailResponse.getContents() );

    }

}
