package com.joojang.bookfriend.api;

import com.joojang.bookfriend.dataResponse.BookDetailResponse;
import com.joojang.bookfriend.dataResponse.DefaultResponse;
import com.joojang.bookfriend.dataResponse.GetKakaoBookSearchResponse;
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

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String Base_URL = "http://joojanghelp.cafe24.com";
    String Base_URL_PATH = "/api/v1";
    String kakaoToken = "KakaoAK 2f818df48b7f3e5ec3b2e81689df6506";


    // 카카오 도서 검색 API
    @Headers({"Authorization: "+kakaoToken})
    @GET("/v3/search/book")
    Call<GetKakaoBookSearchResponse> kakaoBookSearch(@Query("target") String target, @Query("query") String query);


    @POST(Base_URL_PATH + "/auth/refresh_token")
    Call<LoginResponse> refreshToken(@Body RefreshToken refreshToken);

    @POST(Base_URL_PATH + "/auth/login")
    Call<LoginResponse> login(@Body LoginUser loginUser);

    @POST(Base_URL_PATH + "/auth/register")
    Call<JoinResponse> register(@Body JoinUser joinUser);

    @GET(Base_URL_PATH + "/user/books/page/{page}")
    Call<BookListResponse> getUserBooks(@Path("page") int page);

    @POST(Base_URL_PATH + "/user/books")
    Call<DefaultResponse> registerBook(@Body Book book);

    @GET(Base_URL_PATH + "/books/recommend/page/{page}")
    Call<BookListResponse> getRecommendBooks(@Path("page") int page );

    @GET(Base_URL_PATH + "/books/{book_id}/detail")
    Call<BookDetailResponse> getBookDetail(@Path("book_id")int book_id);

    @POST(Base_URL_PATH + "/user/books/activity")
    Call<DefaultResponse> registerReply(@Body BookReply bookReply);

    @GET(Base_URL_PATH + "/user/setting")
    Call<SettingInfoResponse> getSettingInfo();

    @POST(Base_URL_PATH + "/user/setting/activity_state")
    Call<DefaultResponse> changeActivityState(@Body ActivityState activityState);

    @POST(Base_URL_PATH + "/user/books/recommend/read")
    Call<DefaultResponse> changeReadState(@Body ReadState readState);

}
