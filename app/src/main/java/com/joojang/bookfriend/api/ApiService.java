package com.joojang.bookfriend.api;

import com.joojang.bookfriend.data.GetKakaoBookSearchResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // 카카오 책 검색
    final String Base_URL = "https://dapi.kakao.com";
    final String Base_URL_PATH = "";

//    final  String KakaoBookSearchURL = "https://dapi.kakao.com/v3/search/book";
    // 로컬 
//    final String Base_URL = "http://192.168.10.49:8080";
//    final String Base_URL_PATH = "";

    /*
     *  서버 통신 API
     * */

    @Headers({"Authorization: KakaoAK 2f818df48b7f3e5ec3b2e81689df6506"})
    @GET("/v3/search/book")
    Call<GetKakaoBookSearchResponse> kakaoBookSearch(@Query("target") String target, @Query("query") String query);

//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST(Base_URL_PATH + "/user/checkemail")
//    Call<CheckEmailResponse> checkEmail(@Body UserModel userModel);


}
