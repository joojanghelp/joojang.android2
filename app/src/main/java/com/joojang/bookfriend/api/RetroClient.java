package com.joojang.bookfriend.api;

import android.content.Context;
import android.util.Log;

import com.joojang.bookfriend.data.GetKakaoBookSearchResponse;
import com.joojang.bookfriend.data.LoginResponse;
import com.joojang.bookfriend.model.LoginUser;


import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    public ApiService apiService;
    public static String baseUrl = ApiService.Base_URL;
    private static Context mContext;
    private static Retrofit retrofit;

    private static class SingletonHolder {
        private static RetroClient INSTANCE = new RetroClient(mContext);
    }

    public static RetroClient getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }


    public RetroClient(Context context) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
////                .addInterceptor(interceptor) // This is used to add ApplicationInterceptor.
                .build();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

    public RetroClient(Context context, String host) {

        if ( !host.equals("kakao") ) {
            return;
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://dapi.kakao.com")
                .client(okHttpClient)
                .build();
    }

//    Interceptor interceptor = new Interceptor() {
//        @Override
//        public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
//            Request newRequest;
//            // 모든 API 헤더에 토큰 추가
//            SharedPreferences setting = mContext.getSharedPreferences("autoLogin", 0);
//            String token = setting.getString(LoginModel.token, "");
//            newRequest = chain.request().newBuilder()
//                    .addHeader("token", token)
//                    .build();
//
//            return chain.proceed(newRequest);
//        }
//    };


    public RetroClient createBaseApi() {
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


    public void login(LoginUser loginUser, final RetroCallback callback) {
        apiService.login(loginUser).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d( "RetroClient","onResponse : "+response);
                if (response.isSuccessful()) {
                    String result = response.body().toString();
                    callback.onSuccess(response.code(), response.body());
                }else{
                    Log.d( "RetroClient","onResponse not success : "+response.isSuccessful());
                    Log.d( "RetroClient","onResponse not success : "+response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d( "RetroClient","onFailure :"+t.getMessage());
                callback.onError(t);
            }
        });
    }
}
