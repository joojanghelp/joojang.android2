package com.joojang.bookfriend.api;

import com.joojang.bookfriend.dataResponse.BookDetailResponse;
import com.joojang.bookfriend.dataResponse.DefaultResponse;
import com.joojang.bookfriend.dataResponse.GetKakaoBookSearchResponse;
import com.joojang.bookfriend.dataResponse.JoinResponse;
import com.joojang.bookfriend.dataResponse.LoginResponse;
import com.joojang.bookfriend.dataResponse.BookListResponse;
import com.joojang.bookfriend.dataResponse.RecommGubunResponse;
import com.joojang.bookfriend.dataResponse.SettingInfoResponse;
import com.joojang.bookfriend.model.ActivitySetting;
import com.joojang.bookfriend.model.Book;
import com.joojang.bookfriend.model.BookReply;
import com.joojang.bookfriend.model.JoinUser;
import com.joojang.bookfriend.model.LoginUser;
import com.joojang.bookfriend.model.ReadState;
import com.joojang.bookfriend.model.RefreshToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String Base_URL = "http://joojanghelp.cafe24.com";
    String Base_URL_PATH = "";
    String kakaoToken = "KakaoAK 2f818df48b7f3e5ec3b2e81689df6506";


    // 카카오 도서 검색 API
    @Headers({"Authorization: "+kakaoToken})
    @GET("/v3/search/book")
    Call<GetKakaoBookSearchResponse> kakaoBookSearch(@Query("target") String target, @Query("query") String query);


    @POST(Base_URL_PATH + "/api/v1/auth/refresh_token")
    Call<LoginResponse> refreshToken(@Body RefreshToken refreshToken);

    @POST(Base_URL_PATH + "/api/v1/auth/login")
    Call<LoginResponse> login(@Body LoginUser loginUser);

    @POST(Base_URL_PATH + "/api/v1/auth/register")
    Call<JoinResponse> register(@Body JoinUser joinUser);

    @GET(Base_URL_PATH + "/api/v1/user/books/page/{page}")
    Call<BookListResponse> getUserBooks(@Path("page") int page);

    @POST(Base_URL_PATH + "/api/v1/user/books")
    Call<DefaultResponse> registerBook(@Body Book book);

    @GET(Base_URL_PATH + "/api/v1/books/{gubun}/recommend/page/{page}")
    Call<BookListResponse> getRecommendBooks(@Path("gubun")String gubun, @Path("page") int page );

    @GET(Base_URL_PATH + "/api/v1/books/{book_id}/detail")
    Call<BookDetailResponse> getBookDetail(@Path("book_id")int book_id);

    @POST(Base_URL_PATH + "/api/v1/user/books/activity")
    Call<DefaultResponse> registerReply(@Body BookReply bookReply);

    @GET(Base_URL_PATH + "/api/v1/user/setting")
    Call<SettingInfoResponse> getSettingInfo();

    @POST(Base_URL_PATH + "/api/v1/user/setting/activity_state")
    Call<DefaultResponse> changeActivityState(@Body ActivitySetting activitySetting);

    @POST(Base_URL_PATH + "/api/v1/user/books/recommend/read")
    Call<DefaultResponse> changeReadState(@Body ReadState readState);

    @HTTP(method = "DELETE", path = "/api/v1/user/books/recommend/read", hasBody = true)
    Call<DefaultResponse> deleteReadState(@Body ReadState readState);

    @GET(Base_URL_PATH + "/api/v1/system/commoncode/group/{group}/list")
    Call<RecommGubunResponse> getRecommGubun(@Path("group")String group);

    @HTTP(method = "DELETE", path = "/api/v1/user/books/activity", hasBody = true)
    Call<DefaultResponse> deleteActivity(@Body ActivitySetting activitySetting);
}
