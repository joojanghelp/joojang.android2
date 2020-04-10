package com.joojang.bookfriend.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joojang.bookfriend.BaseApplication;
import com.joojang.bookfriend.R;
import com.joojang.bookfriend.adapter.AdapterListBook;
import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.dataResponse.BookListResponse;
import com.joojang.bookfriend.model.Book;
import com.joojang.bookfriend.model.RecommGubun;
import com.joojang.bookfriend.utils.Tools;
import com.joojang.bookfriend.utils.Util;
import com.joojang.bookfriend.widget.SpacingItemDecoration;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class RecommBookFragment extends Fragment {

    private final String TAG = RecommBookFragment.class.getSimpleName();
    private RetroClient retroClient;

    private ViewGroup rootView;
    private Context mContext;

    private RecyclerView recyclerView;
    private AdapterListBook mAdapter;

    private ArrayList<Book> items = new ArrayList<>();

    static int CHANGE_READ_STATE = 1;

    private int page = 1;
    private int curPage = 0;
    private boolean isPageEnd = false;

    private String[] array_name;
    private String[] array_code;
    private int mSelectedItem = 0;
    private String mSelectedCode;
    private EditText et_gubun;

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
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recomm_book, container, false);

        retroClient = RetroClient.getInstance(getActivity()).createBaseApi();

        array_code = BaseApplication.getInstance().gubun_code();
        array_name = BaseApplication.getInstance().gubun_name();

        initComponent();

        return rootView;
    }

    private void initComponent() {

        et_gubun = rootView.findViewById(R.id.et_gubun);
        et_gubun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStateDialog(v);
            }
        });

        mAdapter = new AdapterListBook(mContext, items);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
        recyclerView.addItemDecoration(new SpacingItemDecoration(1, Tools.dpToPx(mContext, 12), true));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListBook.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Book obj, int position) {
                Intent intent = new Intent( mContext , BookDetailActivity.class);
                Book book = items.get(position);
                intent.putExtra("book_id",book.getBook_id());
                startActivityForResult(intent,CHANGE_READ_STATE);
            }
        });

        mSelectedCode = array_code[0];
        String gubun = Util.getGubunPreferences(getActivity());
        if ( gubun != null && gubun.length() !=0 ){
            mSelectedCode = gubun;
            for(int i=0 ; i<array_code.length ; i++){
                if ( gubun.equals( array_code[i] ) ){
                    mSelectedItem = i;
                }
            }
        }
        proc_getRecommendBooks(mSelectedCode);
        et_gubun.setText(array_name[mSelectedItem]);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition()+1;
                int itemTotalCount = recyclerView.getAdapter().getItemCount();

                Log.d(TAG,"scroll:"+lastVisibleItemPosition+":"+itemTotalCount);
                if ( lastVisibleItemPosition >= itemTotalCount ){
                    Log.d(TAG,"바닥에 도착");
                    if ( !isPageEnd ) {
                        page++;
                        proc_getRecommendBooks(mSelectedCode);
                    }
                }

            }
        });

    }

    private void setData(BookListResponse bookListResponse){
        items.addAll(bookListResponse.getItems());
        mAdapter.notifyDataSetChanged();
    }

    private void proc_getRecommendBooks(String mSelectedCode){

        if ( page == curPage ) return;

        retroClient.getRecommendBooks(mSelectedCode, page , new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "proc_getRecommendBooks onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "proc_getRecommendBooks onSuccess :"+code);
                BookListResponse bookListResponse = (BookListResponse) receiveData;
                if (bookListResponse.getItems() != null) {
                    Log.d(TAG, "proc_getRecommendBooks result size: " + bookListResponse.getItems().size() );
                    if ( bookListResponse.getLast_page() == page ){
                        isPageEnd = true;
                    }
                    curPage = page;
                    setData(bookListResponse);
                }else{
                    items.clear();
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(int code, String message) {
                Log.d( TAG,"onFail : "+code);
                Log.d( TAG,"onFail : "+message);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == CHANGE_READ_STATE ){
            if ( resultCode == RESULT_OK ) {
                int book_id = data.getIntExtra("book_id",-1);
                String state = data.getStringExtra("read_state");
                changeBookState(book_id,state);
            }
        }

    }

    private void changeBookState(int book_id,String state){
        for(int i=0 ; i<items.size() ; i++){
            Book book = items.get(i);

            if ( book.getBook_id() == book_id ){
                if ( state.equals("Y")) {
                    book.setRead_check(true);
                }else{
                    book.setRead_check(false);
                }
                items.set(i,book);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private void showStateDialog(final View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("권장도서 그룹 선택");
        builder.setSingleChoiceItems(array_name, mSelectedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) v).setText(array_name[i]);
                mSelectedItem = i;
                mSelectedCode = array_code[i];
                page = 1;
                curPage = 0;
                items.clear();
                Util.saveGubunPreferences(getActivity(),mSelectedCode);
                proc_getRecommendBooks(mSelectedCode);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

}
