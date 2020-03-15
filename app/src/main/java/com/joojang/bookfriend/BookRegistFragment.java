package com.joojang.bookfriend;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.joojang.bookfriend.api.ApiResultCode;
import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.data.GetKakaoBookSearchResponse;
import com.joojang.bookfriend.model.KakaoBookDocument;
import com.joojang.bookfriend.utils.Tools;

public class BookRegistFragment extends Fragment implements View.OnClickListener {

    private final String TAG = BookRegistFragment.class.getSimpleName();

    private Button bt_search;
    private EditText et_isbn , et_title, et_author, et_publisher ;
    private ImageView image_searchbook;
    private TextView tv_content;

    private RetroClient retroClient;
    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        this.mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_book_regist, container, false);

        retroClient = RetroClient.getInstance(mContext).createBaseApi();

        bt_search = rootView.findViewById(R.id.bt_search);
        bt_search.setOnClickListener(this);

        et_isbn = rootView.findViewById(R.id.et_isbn);
        et_title = rootView.findViewById(R.id.et_title);
        et_author = rootView.findViewById(R.id.et_author);
        et_publisher = rootView.findViewById(R.id.et_publisher);
        image_searchbook = rootView.findViewById(R.id.image_searchbook);

        tv_content = rootView.findViewById(R.id.tv_content);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ){
            case  R.id.bt_search:
                bookSearch();
                break;
        }
    }

    private void bookSearch(){
        Log.d(TAG, "bookSearch");

        String isbn = et_isbn.getText().toString().trim();
        if ( isbn == null || isbn.length() == 0 ){
            Toast.makeText(mContext,"isbn 을 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }

        retroClient.kakaoBookSearch("isbn",isbn, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "kakaoBookSearch onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "kakaoBookSearch onSuccess ");

                GetKakaoBookSearchResponse getKakaoBookSearchResponse = (GetKakaoBookSearchResponse) receiveData;

                if (getKakaoBookSearchResponse != null) {

                    Log.d(TAG, "getKakaoBookSearchResponse result : " + getKakaoBookSearchResponse.toString() );

                    if ( getKakaoBookSearchResponse.getDocuments().size() < 1 ){
                        Log.d(TAG, "getKakaoBookSearchResponse result : size :"+getKakaoBookSearchResponse.getDocuments().size()  );
                        return;
                    }
                    KakaoBookDocument kakaoBookDocument = getKakaoBookSearchResponse.getDocuments().get(0);
                    Log.d(TAG, "getKakaoBookSearchResponse result : " + kakaoBookDocument.getContents() );
                    setData(kakaoBookDocument);

                }
            }
        });
    }

    private void setData(KakaoBookDocument kakaoBookDocument){

        et_title.setText( kakaoBookDocument.getTitle() );
        et_author.setText( kakaoBookDocument.getAllAuthors() );
        et_publisher.setText( kakaoBookDocument.getPublisher() );
        tv_content.setText( kakaoBookDocument.getContents() );

        String image_url = "";
        if ( kakaoBookDocument.getThumbnail() != null ){
            image_url = kakaoBookDocument.getThumbnail();
        }
        Tools.displayImageOriginal(mContext, image_searchbook , image_url);

    }
}
