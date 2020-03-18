package com.joojang.bookfriend.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.joojang.bookfriend.R;
import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.api.RetroClientKakao;
import com.joojang.bookfriend.dataResponse.DefaultResponse;
import com.joojang.bookfriend.dataResponse.GetKakaoBookSearchResponse;
import com.joojang.bookfriend.model.Book;
import com.joojang.bookfriend.model.KakaoBookDocument;
import com.joojang.bookfriend.utils.Tools;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class BookRegistFragment extends Fragment implements View.OnClickListener, ZXingScannerView.ResultHandler {

    private final String TAG = BookRegistFragment.class.getSimpleName();

    private Button bt_search, bt_regist;
    private EditText et_isbn, et_title, et_author, et_publisher;
    private ImageView image_searchbook;
    private TextView tv_content;
    private LinearLayout ll_search_book;

    private String mImage_url = "";

    private RetroClientKakao retroClientKakao;
    private RetroClient retroClient;
    private Context mContext;

    private CaptureManager manager;
    private DecoratedBarcodeView barcodeView;

    private ZXingScannerView mScannerView;

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

        retroClientKakao = RetroClientKakao.getInstance(getActivity()).createBaseApi();
        retroClient = RetroClient.getInstance(getActivity()).createBaseApi();

        bt_search = rootView.findViewById(R.id.bt_search);
        bt_search.setOnClickListener(this);

        bt_regist = rootView.findViewById(R.id.bt_regist);
        bt_regist.setOnClickListener(this);

        et_isbn = rootView.findViewById(R.id.et_isbn);
        et_title = rootView.findViewById(R.id.et_title);
        et_author = rootView.findViewById(R.id.et_author);
        et_publisher = rootView.findViewById(R.id.et_publisher);
        image_searchbook = rootView.findViewById(R.id.image_searchbook);

        tv_content = rootView.findViewById(R.id.tv_content);

        ll_search_book = rootView.findViewById(R.id.ll_search_book);
        ll_search_book.setVisibility(View.GONE);
        bt_regist.setVisibility(View.GONE);

        FrameLayout cameraLayout = (FrameLayout) rootView.findViewById(R.id.fl_camera_layout);
        mScannerView = new ZXingScannerView(getActivity());
        List<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.EAN_13);
        formats.add(BarcodeFormat.EAN_8);
        mScannerView.setFormats(formats);
        cameraLayout.addView(mScannerView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_search:
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow( et_isbn.getWindowToken(), 0);
                proc_bookSearch();
                break;

            case R.id.bt_regist:
                proc_bookRegist();

        }
    }

    private void proc_bookSearch() {
        Log.d(TAG, "bookSearch");

        String isbn = et_isbn.getText().toString().trim();
        if (isbn == null || isbn.length() == 0) {
            Toast.makeText(mContext, "isbn 을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        retroClientKakao.kakaoBookSearch("isbn", isbn, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "kakaoBookSearch onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "kakaoBookSearch onSuccess ");

                GetKakaoBookSearchResponse getKakaoBookSearchResponse = (GetKakaoBookSearchResponse) receiveData;

                if (getKakaoBookSearchResponse != null) {

                    Log.d(TAG, "getKakaoBookSearchResponse result : " + getKakaoBookSearchResponse.toString());

                    if (getKakaoBookSearchResponse.getDocuments().size() < 1) {
                        Log.d(TAG, "getKakaoBookSearchResponse result : size :" + getKakaoBookSearchResponse.getDocuments().size());
                        return;
                    }
                    KakaoBookDocument kakaoBookDocument = getKakaoBookSearchResponse.getDocuments().get(0);
                    Log.d(TAG, "getKakaoBookSearchResponse result : " + kakaoBookDocument.getContents());
                    setData(kakaoBookDocument);

                }
            }

            @Override
            public void onFail(int code, String message) {
                Log.d(TAG, "onFail : " + code);
                Log.d(TAG, "onFail : " + message);
            }
        });
    }

    private void setData(KakaoBookDocument kakaoBookDocument) {

        ll_search_book.setVisibility(View.VISIBLE);
        bt_regist.setVisibility(View.VISIBLE);

        et_title.setText(kakaoBookDocument.getTitle());
        et_author.setText(kakaoBookDocument.getAllAuthors());
        et_publisher.setText(kakaoBookDocument.getPublisher());

        if (kakaoBookDocument.getContents().length() != 0) {
            tv_content.setText(kakaoBookDocument.getContents() + "...");
        }

        if (kakaoBookDocument.getThumbnail() != null) {
            mImage_url = kakaoBookDocument.getThumbnail();
        }
        Tools.displayImageOriginal(mContext, image_searchbook, mImage_url);

        mScannerView.resumeCameraPreview(this);
    }

    private void reset(){
        ll_search_book.setVisibility(View.GONE);
        bt_regist.setVisibility(View.GONE);

        et_title.setText("");
        et_author.setText("");
        et_publisher.setText("");
        tv_content.setText("");
        Tools.displayImageOriginal(mContext, image_searchbook, null);
        mImage_url="";
    }

    @Override
    public void handleResult(Result result) {
        Log.d(TAG, "handleResult result " + result.getText());
        String scanResult = result.getText();
        if (scanResult == null || scanResult.length() == 0) {
            return;
        }

        reset();

        et_isbn.setText(scanResult);
        proc_bookSearch();
    }

    private void proc_bookRegist(){

        String uuid = et_isbn.getText().toString().trim();
        String authors = et_author.getText().toString().trim();
        String contents = tv_content.getText().toString();
        String isbn = et_isbn.getText().toString().trim();  // isbn
        String publisher = et_publisher.getText().toString().trim();
        String thumbnail = mImage_url;
        String title = et_title.getText().toString().trim();

        Book book = new Book();
        book.setUuid( Long.parseLong( uuid ) );
        book.setAuthors( authors );
        book.setContents( contents );
        book.setIsbn( isbn );
        book.setPublisher( publisher );
        book.setThumbnail( thumbnail );
        book.setTitle( title );

        retroClient.registerBook(book, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "registerBook onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "registerBook onSuccess ");

                DefaultResponse defaultResponse = (DefaultResponse) receiveData;

                if ( code == 201 ) {
                    Log.d(TAG, "defaultResponse result : " + defaultResponse.getMessage() );
                    reset();
                }else{
                    Log.d(TAG, "defaultResponse result : " + code );
                }
            }

            @Override
            public void onFail(int code, String message) {
                Log.d(TAG, "onFail : " + code);
                Log.d(TAG, "onFail : " + message);
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                reset();
            }
        });

    }
}
