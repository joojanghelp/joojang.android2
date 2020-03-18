package com.joojang.bookfriend.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.joojang.bookfriend.BaseApplication;
import com.joojang.bookfriend.common.Constants;
import com.joojang.bookfriend.dataResponse.DefaultResponse;
import com.joojang.bookfriend.dataResponse.GetKakaoBookSearchResponse;
import com.joojang.bookfriend.dataResponse.JoinResponse;
import com.joojang.bookfriend.dataResponse.LoginResponse;
import com.joojang.bookfriend.dataResponse.UserBookListResponse;
import com.joojang.bookfriend.model.Book;
import com.joojang.bookfriend.model.JoinUser;
import com.joojang.bookfriend.model.LoginUser;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClientKakao {

    private final String TAG = RetroClientKakao.class.getSimpleName();

    public ApiService apiService;
    private static Context mContext;
    private static Retrofit retrofit;

    private static class SingletonHolder {
        private static RetroClientKakao INSTANCE = new RetroClientKakao(mContext);
    }

    public static RetroClientKakao getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }

    public RetroClientKakao(Context context) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://dapi.kakao.com")
                .client(okHttpClient)
                .build();
    }

    public RetroClientKakao createBaseApi() {
        apiService = create(ApiService.class);
        return this;
    }

    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null");
        }
        return retrofit.create(service);
    }


    public void kakaoBookSearch(String target, String query, final RetroCallback callback) {
        apiService.kakaoBookSearch(target,query).enqueue(new Callback<GetKakaoBookSearchResponse>() {
            @Override
            public void onResponse(Call<GetKakaoBookSearchResponse> call, Response<GetKakaoBookSearchResponse> response) {
                Log.d( "RetroClient","onResponse"+response);
                if (response.isSuccessful()) {
                    String result = response.body().toString();
                    callback.onSuccess(response.code(), response.body());
                }else{
                    Log.d( "RetroClient","onResponse not success : "+response.isSuccessful());
                    Log.d( "RetroClient","onResponse not success : "+response.message());
                }
            }

            @Override
            public void onFailure(Call<GetKakaoBookSearchResponse> call, Throwable t) {
                Log.d( "RetroClient","onFailure :"+t.getMessage());
                callback.onError(t);
            }
        });
    }


}
