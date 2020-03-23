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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joojang.bookfriend.BaseApplication;
import com.joojang.bookfriend.R;
import com.joojang.bookfriend.adapter.AdapterGridTwoLineLight;
import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.dataResponse.BookListResponse;
import com.joojang.bookfriend.model.Book;
import com.joojang.bookfriend.utils.Tools;
import com.joojang.bookfriend.widget.SpacingItemDecoration;

import java.util.ArrayList;

public class BookFragment extends Fragment {

    private final String TAG = BookFragment.class.getSimpleName();
    private RetroClient retroClient;

    private ViewGroup rootView;
    private Context mContext;

    private RecyclerView recyclerView;
    private AdapterGridTwoLineLight mAdapter;

    private ArrayList<Book> items = new ArrayList<>();

    private int page = 1;
    private boolean isPageEnd = false;

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
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_book, container, false);

        retroClient = RetroClient.getInstance(getActivity()).createBaseApi();
        initComponent();
        return rootView;
    }

    private void initComponent() {

        mAdapter = new AdapterGridTwoLineLight(getActivity(), items);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(getActivity(), 10), true));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridTwoLineLight.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Book obj, int position) {
                Intent intent = new Intent( mContext , BookDetailActivity.class);
                Book book = items.get(position);
                intent.putExtra("book_id",book.getBook_id());
                startActivity(intent);
            }
        });

        page = 1;
        items.clear();
        isPageEnd = false;
        proc_getUserBooks();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition()+1;
                int itemTotalCount = recyclerView.getAdapter().getItemCount();

                Log.d(TAG,"scroll:"+lastVisibleItemPosition+":"+itemTotalCount);
                if ( lastVisibleItemPosition == itemTotalCount ){
                    Log.d(TAG,"바닥에 도착");
                    if ( !isPageEnd ) {
                        page++;
                        proc_getUserBooks();
                    }
                }

            }
        });

    }

    private void setData(BookListResponse bookListResponse){
        items.addAll(bookListResponse.getItems());
        mAdapter.notifyDataSetChanged();
    }


    private void proc_getUserBooks(){

        Log.d(TAG, "proc_getUserBooks:"+ BaseApplication.getInstance().getBearerLOGINTOKEN());

        retroClient.getUserBooks(page, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "proc_getUserBooks onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "proc_getUserBooks onSuccess :"+code);
                BookListResponse bookListResponse = (BookListResponse) receiveData;
                if (bookListResponse.getItems() != null) {
                    Log.d(TAG, "proc_getUserBooks result size: " + bookListResponse.getItems().size() );
                    if ( bookListResponse.getLast_page() == page ){
                        isPageEnd = true;
                    }
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
