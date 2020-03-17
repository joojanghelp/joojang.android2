package com.joojang.bookfriend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.joojang.bookfriend.adapter.AdapterGridTwoLineLight;
import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.data.LoginResponse;
import com.joojang.bookfriend.data.UserBookListResponse;
import com.joojang.bookfriend.login.LoginSimpleLight;
import com.joojang.bookfriend.model.Book;
import com.joojang.bookfriend.model.Image;
import com.joojang.bookfriend.model.LoginUser;
import com.joojang.bookfriend.utils.Tools;
import com.joojang.bookfriend.widget.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment {

    private final String TAG = BookFragment.class.getSimpleName();
    private RetroClient retroClient;

    private ViewGroup rootView;
    private Context mContext;

    private RecyclerView recyclerView;
    private AdapterGridTwoLineLight mAdapter;

    private ArrayList<Book> items;

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
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(mContext, 10), true));
        recyclerView.setHasFixedSize(true);

        mAdapter = new AdapterGridTwoLineLight(mContext, items);
        recyclerView.setAdapter(mAdapter);
        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridTwoLineLight.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Book obj, int position) {
                Intent intent = new Intent( mContext , BookDetailActivity.class);
                startActivity(intent);
            }
        });


        proc_getUserBooks();

    }

    private void setData(UserBookListResponse userBookListResponse){
        items = userBookListResponse.getItems();

        //set data and list adapter
        mAdapter.notifyDataSetChanged();
    }


    private void proc_getUserBooks(){
        Log.d(TAG, "proc_getUserBooks:"+BaseApplication.getInstance().getBearerLOGINTOKEN());

        retroClient.getUserBooks(BaseApplication.getInstance().getBearerLOGINTOKEN() , new RetroCallback() {
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
