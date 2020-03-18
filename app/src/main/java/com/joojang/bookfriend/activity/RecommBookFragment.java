package com.joojang.bookfriend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joojang.bookfriend.BaseApplication;
import com.joojang.bookfriend.R;
import com.joojang.bookfriend.adapter.AdapterListBasic;
import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.dataResponse.BookListResponse;
import com.joojang.bookfriend.model.Book;
import com.joojang.bookfriend.model.Image;
import com.joojang.bookfriend.utils.Tools;
import com.joojang.bookfriend.widget.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class RecommBookFragment extends Fragment {

    private final String TAG = RecommBookFragment.class.getSimpleName();
    private RetroClient retroClient;

    private ViewGroup rootView;
    private Context mContext;

    private RecyclerView recyclerView;
    private AdapterListBasic mAdapter;

    private ArrayList<Book> items = new ArrayList<>();

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

        initComponent();

        return rootView;
    }

    private void initComponent() {
        mAdapter = new AdapterListBasic(mContext, items);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
        recyclerView.addItemDecoration(new SpacingItemDecoration(1, Tools.dpToPx(mContext, 12), true));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListBasic.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Book obj, int position) {
                Intent intent = new Intent( mContext , BookDetailActivity.class);
                startActivity(intent);
            }
        });

        proc_getRecoomendBooks();

    }

    private void setData(BookListResponse bookListResponse){
        items.clear();
        items.addAll(bookListResponse.getItems());
        mAdapter.notifyDataSetChanged();
    }

    private void proc_getRecoomendBooks(){

        retroClient.getRecommendBooks(new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "proc_getRecoomendBooks onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "proc_getRecoomendBooks onSuccess :"+code);
                BookListResponse bookListResponse = (BookListResponse) receiveData;
                if (bookListResponse != null) {
                    Log.d(TAG, "proc_getRecoomendBooks result size: " + bookListResponse.getItems().size() );
                    setData(bookListResponse);
                }
            }

            @Override
            public void onFail(int code, String message) {
                Log.d( TAG,"onFail : "+code);
                Log.d( TAG,"onFail : "+message);
            }
        });
    }




}
