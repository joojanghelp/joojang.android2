package com.joojang.bookfriend;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.joojang.bookfriend.adapter.AdapterGridTwoLineLight;
import com.joojang.bookfriend.model.Image;
import com.joojang.bookfriend.utils.Tools;
import com.joojang.bookfriend.widget.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment {

    private ViewGroup rootView;
    private Context mContext;

    private RecyclerView recyclerView;
    private AdapterGridTwoLineLight mAdapter;

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

        initComponent();

        return rootView;
    }

    private void initComponent() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(mContext, 12), true));
        recyclerView.setHasFixedSize(true);

        List<Image> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Image obj = new Image();
            obj.image = "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F5131808%3Ftimestamp%3D20200311135748";
            if ( i%4 == 1 ){
                obj.image = "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F463250%3Ftimestamp%3D20200228123611";
            }else if ( i%4 == 2){
                obj.image = "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F589611%3Ftimestamp%3D20200310124439";
            }else if ( i%4 == 3){
                obj.image = "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F615308%3Ftimestamp%3D20200308122633";
            }
            obj.name = "한국사 대모험";
            obj.brief = "2020/03/12";
            obj.counter = 3;
            items.add(obj);
        }


        //set data and list adapter
        mAdapter = new AdapterGridTwoLineLight(mContext, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridTwoLineLight.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Image obj, int position) {
                Snackbar.make(rootView, obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }




}
