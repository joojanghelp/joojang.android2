package com.joojang.bookfriend.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joojang.bookfriend.R;
import com.joojang.bookfriend.adapter.AdapterListBasic;
import com.joojang.bookfriend.model.Image;
import com.joojang.bookfriend.utils.Tools;
import com.joojang.bookfriend.widget.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class RecommBookFragment extends Fragment {

    private ViewGroup rootView;
    private Context mContext;

    private RecyclerView recyclerView;
    private AdapterListBasic mAdapter;

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

        initComponent();

        return rootView;
    }

    private void initComponent() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
        recyclerView.addItemDecoration(new SpacingItemDecoration(1, Tools.dpToPx(mContext, 12), true));
        recyclerView.setHasFixedSize(true);

        List<Image> items = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            Image obj = new Image();
//            obj.image = "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F5131808%3Ftimestamp%3D20200311135748";
//            if ( i%4 == 1 ){
//                obj.image = "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F463250%3Ftimestamp%3D20200228123611";
//            }else if ( i%4 == 2){
//                obj.image = "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F589611%3Ftimestamp%3D20200310124439";
//            }else if ( i%4 == 3){
//                obj.image = "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F615308%3Ftimestamp%3D20200308122633";
//            }
//            obj.name = "한국사 대모험";
//            obj.brief = "2020/03/12";
//            obj.counter = 3;
//            items.add(obj);
//        }
//        100층짜리 집	이와이 도시오	북뱅크	9788989863786 	https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1309566%3Ftimestamp%3D20200315134831%3Fmoddttm=202003151431
//        1층에 사는 키 작은 할머니	샤를로트 벨리에르/이안 드 해스	키즈엠	9788967491284 	https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F938371%3Ftimestamp%3D20200315133539%3Fmoddttm=202003151432
//        1학년 수학 동화	우리 기획	예림당	9788930205528 	https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F472267%3Ftimestamp%3D20190123083151%3Fmoddttm=202003151432
//        1학년 이솝 우화	이솝	효리원	9788983393463 	https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1164542%3Ftimestamp%3D20190127025007%3Fmoddttm=202003151433
//        1학년을 위한 우수 동시	김종상	예림당	9788930205818 	https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F472373%3Ftimestamp%3D20190123083222%3Fmoddttm=202003151433

//        1학년이 만나는 과학	박진명	글사랑	9788970283104 	https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F956977%3Ftimestamp%3D20200228125526%3Fmoddttm=202003151434
//        가을을 만났어요	이미애	보림	9788943304782 	https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F589611%3Ftimestamp%3D20200312133523%3Fmoddttm=202003151434
//        가을을 파는 마법사	이종은/류은형	노루궁뎅이	9788967653798 	https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F939293%3Ftimestamp%3D20200315132138%3Fmoddttm=202003151434
//        가족은 꼬옥 안아 주는 거야	박윤경/김이랑	웅진주니어	9788901121345 	https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F348932%3Ftimestamp%3D20200315140000%3Fmoddttm=202003151434
//        강아지똥	권정생	길벗어린이	8986621134 	https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1243255%3Ftimestamp%3D20200315134828%3Fmoddttm=202003151434

        Image obj = new Image();
        obj.image = "https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1309566%3Ftimestamp%3D20200315134831%3Fmoddttm=202003151431";
        obj.name = "100층짜리 집";
        obj.brief = "2020/03/12";
        obj.counter = 1;
        items.add(obj);

        obj = new Image();
        obj.image = "https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F938371%3Ftimestamp%3D20200315133539%3Fmoddttm=202003151432";
        obj.name = "1층에 사는 키 작은 할머니";
        obj.brief = "2020/03/12";
        obj.counter = 3;
        items.add(obj);

        obj = new Image();
        obj.image = "https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F472267%3Ftimestamp%3D20190123083151%3Fmoddttm=202003151432";
        obj.name = "1학년 수학 동화";
        obj.brief = "2020/03/12";
        obj.counter = 3;
        items.add(obj);

        obj = new Image();
        obj.image = "https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1164542%3Ftimestamp%3D20190127025007%3Fmoddttm=202003151433";
        obj.name = "1학년 이솝 우화";
        obj.brief = "2020/03/12";
        obj.counter = 1;
        items.add(obj);

        obj = new Image();
        obj.image = "https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F472373%3Ftimestamp%3D20190123083222%3Fmoddttm=202003151433";
        obj.name = "1학년을 위한 우수 동시";
        obj.brief = "2020/03/12";
        obj.counter = 3;
        items.add(obj);

        obj = new Image();
        obj.image = "https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F956977%3Ftimestamp%3D20200228125526%3Fmoddttm=202003151434";
        obj.name = "1학년이 만나는 과학";
        obj.brief = "2020/03/12";
        obj.counter = 3;
        items.add(obj);

        obj = new Image();
        obj.image = "https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F589611%3Ftimestamp%3D20200312133523%3Fmoddttm=202003151434";
        obj.name = "가을을 만났어요";
        obj.brief = "2020/03/12";
        obj.counter = 3;
        items.add(obj);

        obj = new Image();
        obj.image = "https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F939293%3Ftimestamp%3D20200315132138%3Fmoddttm=202003151434";
        obj.name = "가을을 파는 마법사";
        obj.brief = "2020/03/12";
        obj.counter = 3;
        items.add(obj);

        obj = new Image();
        obj.image = "https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F348932%3Ftimestamp%3D20200315140000%3Fmoddttm=202003151434";
        obj.name = "가족은 꼬옥 안아 주는 거야";
        obj.brief = "2020/03/12";
        obj.counter = 3;
        items.add(obj);

        obj = new Image();
        obj.image = "https://search1.kakaocdn.net/thumb/C116x164.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1243255%3Ftimestamp%3D20200315134828%3Fmoddttm=202003151434";
        obj.name = "강아지똥";
        obj.brief = "2020/03/12";
        obj.counter = 3;
        items.add(obj);


        //set data and list adapter
        mAdapter = new AdapterListBasic(mContext, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListBasic.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Image obj, int position) {
                Intent intent = new Intent( mContext , BookDetailActivity.class);
                startActivity(intent);
            }
        });

    }




}
