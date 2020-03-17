package com.joojang.bookfriend.api;

import com.joojang.bookfriend.BaseApplication;
import com.joojang.bookfriend.data.GetKakaoBookSearchResponse;
import com.joojang.bookfriend.data.JoinResponse;
import com.joojang.bookfriend.data.LoginResponse;
import com.joojang.bookfriend.data.UserBookListResponse;
import com.joojang.bookfriend.model.JoinUser;
import com.joojang.bookfriend.model.LoginUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // 카카오 책 검색
    final String Base_URL = "http://joojanghelp.cafe24.com";
    final String Base_URL_PATH = "/api/v1";

    // 로컬 
//    final String Base_URL = "http://192.168.10.49:8080";
//    final String Base_URL_PATH = "";

    // 카카오 도서 검색 API
    @Headers({"Authorization: KakaoAK 2f818df48b7f3e5ec3b2e81689df6506"})
    @GET("/v3/search/book")
    Call<GetKakaoBookSearchResponse> kakaoBookSearch(@Query("target") String target, @Query("query") String query);

    @Headers({"Content-Type: application/json", "Accept: application/json" ,"Request-Client-Type: A01003"})
    @POST(Base_URL_PATH + "/auth/login")
    Call<LoginResponse> login(@Body LoginUser loginUser);

    @Headers({"Content-Type: application/json", "Accept: application/json" ,"Request-Client-Type: A01003"})
    @POST(Base_URL_PATH + "/auth/register")
    Call<JoinResponse> register(@Body JoinUser joinUser);

    @Headers({"Content-Type: application/json", "Accept: application/json" ,"Request-Client-Type: A01003" })
    @GET(Base_URL_PATH + "/user/books")
    Call<UserBookListResponse> getUserBooks(@Header("Authorization") String token);
}
