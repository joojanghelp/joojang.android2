package com.joojang.bookfriend.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.joojang.bookfriend.BaseApplication;
import com.joojang.bookfriend.common.Constants;
import com.joojang.bookfriend.dataResponse.BookDetailResponse;
import com.joojang.bookfriend.dataResponse.DefaultResponse;
import com.joojang.bookfriend.dataResponse.JoinResponse;
import com.joojang.bookfriend.dataResponse.LoginResponse;
import com.joojang.bookfriend.dataResponse.BookListResponse;
import com.joojang.bookfriend.dataResponse.SettingInfoResponse;
import com.joojang.bookfriend.model.ActivityState;
import com.joojang.bookfriend.model.Book;
import com.joojang.bookfriend.model.BookReply;
import com.joojang.bookfriend.model.JoinUser;
import com.joojang.bookfriend.model.LoginUser;
import com.joojang.bookfriend.model.ReadState;
import com.joojang.bookfriend.model.RefreshToken;
import com.joojang.bookfriend.utils.Util;


import java.io.IOException;
import java.util.Date;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private final String TAG = RetroClient.class.getSimpleName();

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
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

    Interceptor interceptor = new Interceptor() {

        @Override
        public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
            Request newRequest;
            String token = BaseApplication.getInstance().getLOGINTOKEN();
            String bearerToken = BaseApplication.getInstance().getBearerLOGINTOKEN();
            // 모든 API 헤더에 토큰 추가
            if ( token != null && !token.equals("") ) {
                Log.d(TAG,"token is not null:"+token);

                int expire_in = BaseApplication.getInstance().getEXPIRES_IN();
                Date loginDate = BaseApplication.getInstance().getLOGINDATE();
                Date expireDate = new Date();

                long diff = expireDate.getTime() - loginDate.getTime();
                long sec = diff/1000;

                Log.d(TAG,"diff:"+expireDate.getTime()+"-"+loginDate.getTime()+"="+diff+","+sec);

                if ( sec > expire_in ){ // 토큰 만료
                    BaseApplication.getInstance().setLOGINTOKEN("");
                    String rtoken = BaseApplication.getInstance().getREFRESHTOKEN();
                    RefreshToken refreshToken = new RefreshToken();
                    refreshToken.setRefresh_token(rtoken);
                    Response<LoginResponse> response = apiService.refreshToken(refreshToken).execute();

                    if ( response.body()!=null ) {
                        Log.d(TAG, "diff:" + response.body().getAccess_token());

                        BaseApplication.getInstance().setLOGINTOKEN(response.body().getAccess_token());
                        BaseApplication.getInstance().setREFRESHTOKEN(response.body().getRefresh_token());
                        BaseApplication.getInstance().setEXPIRES_IN(response.body().getExpires_in());
                        BaseApplication.getInstance().setLOGINDATE(new Date());
                        bearerToken = BaseApplication.getInstance().getBearerLOGINTOKEN();

                        Util.saveAccessTokenPreferences( mContext  , response.body().getAccess_token() );
                        Util.saveRefreshTokenPreferences( mContext, response.body().getRefresh_token() );

                    }else{
                        Log.d(TAG, "diff:" + response.body());
                    }
                }

                newRequest = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .addHeader("Request-Client-Type", Constants.REQUEST_CLIENT_TYPE)
                        .addHeader("Authorization", bearerToken)
                        .build();

            }else{
                Log.d(TAG,"token is null");
                newRequest = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .addHeader("Request-Client-Type", Constants.REQUEST_CLIENT_TYPE)
                        .build();
            }
            return chain.proceed(newRequest);
        }
    };


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


    public void refreshToken(RefreshToken refreshToken , final RetroCallback callback) {
        apiService.refreshToken(refreshToken).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d( "RetroClient","onResponse : "+response);
                if (response.isSuccessful()) {
                    String result = response.body().toString();
                    callback.onSuccess(response.code(), response.body());
                }else{
                    Log.d( "RetroClient","onResponse not success : "+response.code());
                    Log.d( "RetroClient","onResponse not success : "+response.message());
                    callback.onFail(response.code(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
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
                    Log.d( "RetroClient","onResponse not success : "+response.code());
                    Log.d( "RetroClient","onResponse not success : "+response.message());
                    callback.onFail(response.code(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d( "RetroClient","onFailure :"+t.getMessage());
                callback.onError(t);
            }
        });
    }

    public void register(JoinUser joinUser, final RetroCallback callback) {
        apiService.register(joinUser).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                Log.d( "RetroClient","onResponse : "+response);
                if (response.isSuccessful()) {
                    String result = response.body().toString();
                    callback.onSuccess(response.code(), response.body());
                }else{
                    Log.d( "RetroClient","onResponse not success : "+response.code());
                    Log.d( "RetroClient","onResponse not success : "+response.message());
                    callback.onFail(response.code(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Log.d( "RetroClient","onFailure :"+t.getMessage());
                callback.onError(t);
            }
        });
    }

    public void getUserBooks(int page , final RetroCallback callback) {
        apiService.getUserBooks(page ).enqueue(new Callback<BookListResponse>() {
            @Override
            public void onResponse(Call<BookListResponse> call, Response<BookListResponse> response) {
                Log.d( "RetroClient","onResponse : "+response);
                if (response.isSuccessful()) {
                    String result = response.body().toString();
                    callback.onSuccess(response.code(), response.body());
                    Log.d( "RetroClient","onResponse success : "+response.body());
                }else{
                    Log.d( "RetroClient","onResponse not success : "+response.code());
                    Log.d( "RetroClient","onResponse not success : "+response.message());
                    callback.onFail(response.code(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BookListResponse> call, Throwable t) {
                Log.d( "RetroClient","onFailure :"+t.getMessage());
                callback.onError(t);
            }
        });
    }


    public void registerBook(Book book, final RetroCallback callback) {
        apiService.registerBook(book).enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Log.d( "RetroClient","onResponse : "+response);
                if (response.isSuccessful()) {
                    String result = response.body().toString();
                    callback.onSuccess(response.code(), response.body());
                }else{
                    Log.d( "RetroClient","onResponse not success : "+response.code());
                    Log.d( "RetroClient","onResponse not success : "+response.message());
                    DefaultResponse restError=null;
                    try {
                        restError = (DefaultResponse) retrofit.responseBodyConverter(DefaultResponse.class, DefaultResponse.class.getAnnotations()).convert(response.errorBody());
                    }catch (IOException e){

                    }

                    callback.onFail(response.code(), restError.getMessage() );
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.d( "RetroClient","onFailure :"+t.getMessage());
                callback.onError(t);
            }
        });
    }

    public void getRecommendBooks(int page , final RetroCallback callback) {
        apiService.getRecommendBooks(page ).enqueue(new Callback<BookListResponse>() {
            @Override
            public void onResponse(Call<BookListResponse> call, Response<BookListResponse> response) {
                Log.d( "RetroClient","onResponse : "+response);
                if (response.isSuccessful()) {
                    String result = response.body().toString();
                    callback.onSuccess(response.code(), response.body());
                    Log.d( "RetroClient","onResponse success : "+response.body());
                }else{
                    Log.d( "RetroClient","onResponse not success : "+response.code());
                    Log.d( "RetroClient","onResponse not success : "+response.message());
                    callback.onFail(response.code(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BookListResponse> call, Throwable t) {
                Log.d( "RetroClient","onFailure :"+t.getMessage());
                callback.onError(t);
            }
        });
    }

    public void getBookDetail(int book_id , final RetroCallback callback) {
        apiService.getBookDetail(book_id).enqueue(new Callback<BookDetailResponse>() {
            @Override
            public void onResponse(Call<BookDetailResponse> call, Response<BookDetailResponse> response) {
                Log.d( "RetroClient","onResponse : "+response);
                if (response.isSuccessful()) {
                    String result = response.body().toString();
                    callback.onSuccess(response.code(), response.body());
                    Log.d( "RetroClient","onResponse success : "+response.body());
                }else{
                    Log.d( "RetroClient","onResponse not success : "+response.code());
                    Log.d( "RetroClient","onResponse not success : "+response.message());
                    callback.onFail(response.code(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BookDetailResponse> call, Throwable t) {
                Log.d( "RetroClient","onFailure :"+t.getMessage());
                callback.onError(t);
            }
        });
    }

    public void registerReply(BookReply bookReply, final RetroCallback callback) {
        apiService.registerReply(bookReply).enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Log.d( "RetroClient","onResponse : "+response);
                if (response.isSuccessful()) {
                    String result = response.body().toString();
                    callback.onSuccess(response.code(), response.body());
                }else{
                    Log.d( "RetroClient","onResponse not success : "+response.code());
                    Log.d( "RetroClient","onResponse not success : "+response.message());
                    callback.onFail(response.code(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.d( "RetroClient","onFailure :"+t.getMessage());
                callback.onError(t);
            }
        });
    }

    public void getSettingInfo( final RetroCallback callback) {
        apiService.getSettingInfo().enqueue(new Callback<SettingInfoResponse>() {
            @Override
            public void onResponse(Call<SettingInfoResponse> call, Response<SettingInfoResponse> response) {
                Log.d( "RetroClient","onResponse : "+response);
                if (response.isSuccessful()) {
                    String result = response.body().toString();
                    callback.onSuccess(response.code(), response.body());
                    Log.d( "RetroClient","onResponse success : "+response.body());
                }else{
                    Log.d( "RetroClient","onResponse not success : "+response.code());
                    Log.d( "RetroClient","onResponse not success : "+response.message());
                    callback.onFail(response.code(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<SettingInfoResponse> call, Throwable t) {
                Log.d( "RetroClient","onFailure :"+t.getMessage());
                callback.onError(t);
            }
        });
    }

    public void changeActivityState(ActivityState activityState, final RetroCallback callback) {
        apiService.changeActivityState(activityState).enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Log.d( "RetroClient","onResponse : "+response);
                if (response.isSuccessful()) {
                    String result = response.body().toString();
                    callback.onSuccess(response.code(), response.body());
                }else{
                    Log.d( "RetroClient","onResponse not success : "+response.code());
                    Log.d( "RetroClient","onResponse not success : "+response.message());
                    callback.onFail(response.code(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.d( "RetroClient","onFailure :"+t.getMessage());
                callback.onError(t);
            }
        });
    }

    public void changeReadState(ReadState readState, final RetroCallback callback) {
        apiService.changeReadState(readState).enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Log.d( "RetroClient","onResponse : "+response);
                if (response.isSuccessful()) {
                    String result = response.body().toString();
                    callback.onSuccess(response.code(), response.body());
                }else{
                    Log.d( "RetroClient","onResponse not success : "+response.code());
                    Log.d( "RetroClient","onResponse not success : "+response.message());
                    callback.onFail(response.code(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.d( "RetroClient","onFailure :"+t.getMessage());
                callback.onError(t);
            }
        });
    }
}
