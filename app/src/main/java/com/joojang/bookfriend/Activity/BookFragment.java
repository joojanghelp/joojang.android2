package com.joojang.bookfriend.Activity;

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
import com.joojang.bookfriend.adapter.AdapterGridTwoLineLight;
import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.dataResponse.UserBookListResponse;
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

        proc_getUserBooks();

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridTwoLineLight.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Book obj, int position) {
                Intent intent = new Intent( mContext , BookDetailActivity.class);
                startActivity(intent);
            }
        });




    }

    private void setData(UserBookListResponse userBookListResponse){
        items.clear();
        items.addAll(userBookListResponse.getItems());
        mAdapter.notifyDataSetChanged();
    }


    private void proc_getUserBooks(){
        Log.d(TAG, "proc_getUserBooks:"+ BaseApplication.getInstance().getBearerLOGINTOKEN());

        retroClient.getUserBooks(new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "proc_getUserBooks onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "proc_getUserBooks onSuccess :"+code);
                UserBookListResponse userBookListResponse = (UserBookListResponse) receiveData;
                if (userBookListResponse != null) {
                    Log.d(TAG, "proc_getUserBooks result size: " + userBookListResponse.getItems().size() );
                    setData(userBookListResponse);
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
