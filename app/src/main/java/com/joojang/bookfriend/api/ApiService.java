package com.joojang.bookfriend.api;

import com.joojang.bookfriend.dataResponse.DefaultResponse;
import com.joojang.bookfriend.dataResponse.GetKakaoBookSearchResponse;
import com.joojang.bookfriend.dataResponse.JoinResponse;
import com.joojang.bookfriend.dataResponse.LoginResponse;
import com.joojang.bookfriend.dataResponse.BookListResponse;
import com.joojang.bookfriend.model.Book;
import com.joojang.bookfriend.model.JoinUser;
import com.joojang.bookfriend.model.LoginUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    String Base_URL = "http://joojanghelp.cafe24.com";
    String Base_URL_PATH = "/api/v1";
    String kakaoToken = "KakaoAK 2f818df48b7f3e5ec3b2e81689df6506";


    // 카카오 도서 검색 API
    @Headers({"Authorization: "+kakaoToken})
    @GET("/v3/search/book")
    Call<GetKakaoBookSearchResponse> kakaoBookSearch(@Query("target") String target, @Query("query") String query);

    @POST(Base_URL_PATH + "/auth/login")
    Call<LoginResponse> login(@Body LoginUser loginUser);

    @POST(Base_URL_PATH + "/auth/register")
    Call<JoinResponse> register(@Body JoinUser joinUser);

    @GET(Base_URL_PATH + "/user/books")
    Call<BookListResponse> getUserBooks();

    @POST(Base_URL_PATH + "/user/books")
    Call<DefaultResponse> registerBook(@Body Book book);

    @GET(Base_URL_PATH + "/books/recommend")
    Call<BookListResponse> getRecommendBooks();

}
